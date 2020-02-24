package com.ericsson.internal.dtra.projectmanagement.domain.serializer;

import java.io.IOException;
import java.util.Date;

import com.ericsson.internal.dtra.projectmanagement.domain.entity.Project;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkBreakdownStructure;
import com.ericsson.internal.dtra.projectmanagement.domain.entity.WorkPackage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * This class represents a custom serializer for PrimeNG tree table needs.
 * The default serialization would force the client using PrimeNG's tree table
 * to loop through the response to generate the wanted JSON. This process is OK
 * for low density data, but in the case of a detailed Project, we can have
 * multiple WBS and multiple WP per WBS.
 *
 * Hence, in order to reduce the risk of having low performance on the user side,
 * we allow the backend to change it's default serialization on-demand.
 */
public class PrimeNgProjectTreeSerializer extends StdSerializer<Project> {

  private static final long serialVersionUID = -3685525854497846708L;

  public PrimeNgProjectTreeSerializer() {
    this(Project.class);
  }

  public PrimeNgProjectTreeSerializer(Class<Project> projectClass) {
    super(projectClass);
  }

  @Override
  public void serialize(Project project, JsonGenerator generator, SerializerProvider provider) throws IOException {
    generator.writeStartObject();

    generator.writeObjectFieldStart("data");
    generator.writeNumberField("id", project.getId());
    generator.writeStringField("name", project.getName());
    generator.writeStringField("status", project.getStatus());
    generator.writeStringField("region", project.getRegion().getName());
    generator.writeStringField("gsc", project.getGlobalServiceCenter().getName());
    generator.writeStringField("costModel", project.getCostModel().getName());
    generator.writeEndObject();

    // Serialization of the list of WBS
    generator.writeArrayFieldStart("children");
    for (WorkBreakdownStructure wbs : project.getWorkBreakdownStructures()) {
      generator.writeStartObject();
      addChildrenData(generator, wbs);

      // Serialization of the list of WP
      generator.writeArrayFieldStart("children");
      for (WorkPackage workpackage : wbs.getWorkPackages()) {
        generator.writeStartObject();
        addChildrenData(generator, workpackage);
        generator.writeEndObject();
      }
      generator.writeEndArray();
      generator.writeEndObject();
    }
    generator.writeEndArray();

    generator.writeEndObject();
  }

  private void addChildrenData(final JsonGenerator generator, final WorkBreakdownStructure wbs) throws IOException {
    generator.writeObjectFieldStart("data");

    //TODO: Add a format to the date when start and due dates make more sense from a requirement point of view
    Date startDate = wbs.getStartDate();
    Date dueDate = wbs.getDueDate();

    addMandatoryChildrenFields(
          generator,
          wbs.getId(),
          wbs.getName(),
          startDate == null ? null : startDate.toString(),
          dueDate == null ? null : dueDate.toString(),
          wbs.getStatus());

    addOptionalChildrenFields(
          generator,
          wbs.getLabel(),
          wbs.getSite(),
          wbs.getOperationalActivity(),
          wbs.getNetworkActivity(),
          wbs.getPurchaseOrder());

    generator.writeEndObject();
  }

  private void addChildrenData(final JsonGenerator generator, final WorkPackage workPackage) throws IOException {
    generator.writeObjectFieldStart("data");

    //TODO: Add a format to the date when start and due dates make more sense from a requirement point of view
    Date startDate = workPackage.getStartDate();
    Date dueDate = workPackage.getDueDate();

    addMandatoryChildrenFields(
          generator,
          workPackage.getId(),
          workPackage.getName(),
          startDate == null ? null : startDate.toString(),
          dueDate == null ? null : dueDate.toString(),
          workPackage.getStatus());

    // We can reuse the common fields between WBS and WP here
    addOptionalChildrenFields(
          generator,
          workPackage.getWorkBreakdownStructure().getLabel(),
          workPackage.getWorkBreakdownStructure().getSite(),
          workPackage.getWorkBreakdownStructure().getOperationalActivity(),
          workPackage.getWorkBreakdownStructure().getNetworkActivity(),
          workPackage.getWorkBreakdownStructure().getPurchaseOrder());

    // Add the service order which is specific for WP
    generator.writeStringField("serviceOrder", workPackage.getServiceOrder());

    generator.writeEndObject();
  }

  private void addOptionalChildrenFields(
        final JsonGenerator generator,
        final String label,
        final String site,
        final String operationalActivity,
        final String networkActivity,
        final String purchaseOrder)
        throws IOException {
    generator.writeStringField("label", label);
    generator.writeStringField("site", site);
    generator.writeStringField("operationalActivity", operationalActivity);
    generator.writeStringField("networkActivity", networkActivity);
    generator.writeStringField("purchaseOrder", purchaseOrder);
  }

  private void addMandatoryChildrenFields(
        final JsonGenerator generator,
        final Integer id,
        final String name,
        final String startDate,
        final String dueDate,
        final String status) throws IOException {
    generator.writeNumberField("id", id);
    generator.writeStringField("name", name);
    generator.writeStringField("startDate", startDate);
    generator.writeStringField("dueDate", dueDate);
    generator.writeStringField("status", status);
  }
}
