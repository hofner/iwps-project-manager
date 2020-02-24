
import json
import sys
import argparse
import time

parser = argparse.ArgumentParser(description="Print insert statements for country/region/scm mapping")
parser.add_argument("--mapping-file", help="Path to the country region mapping", type=str)
parser.add_argument("--scm-file", help="Path to the country scm ready mapping", type=str)
parser.add_argument("--output", help="Path to the statements", type=str)
args = parser.parse_args()

if __name__ == "__main__":
    # Read the mapping definitions from files
    with open(args.mapping_file) as country_region_json:
        country_region = json.load(country_region_json)

    with open(args.scm_file) as country_scm_ready_json:
        country_scm_ready = json.load(country_scm_ready_json)

    output = open(args.output, 'w+')

    # For each region/country mapping, add insert statement to a file
    for country_region_map in country_region["items"]:

        # Set the default values from the mapping object
        scm_ready_str = 'false'
        country = country_region_map["country"].encode('utf-8').strip()
        region = country_region_map["region"].strip()
        utc_offset = country_region_map["utcoffset"]

        # Verify if the country is scm ready
        for i in xrange(len(country_scm_ready["items"])):
            if country == country_scm_ready["items"][i]["sapeligiblecountry"].encode('utf-8').strip():
                scm_ready_str = 'true'
                country_scm_ready["items"].pop(i)
                break

        # Set the time to UTC + offset in the format of HH:MM. i.e. UTC-05:00
        offset_sign = "-"
        timezone = "UTC"
        if utc_offset < 0:
            offset_sign = "+"
            utc_offset = utc_offset * -1

        if utc_offset != 0:
            timezone = "UTC" + offset_sign + str(time.strftime("%H:%M", time.gmtime(60*utc_offset)))

        # Write it out!
        output.write("INSERT INTO country(region_id, name, standard_cost_model_ready, timezone)" +
                     " VALUES ((SELECT id FROM region WHERE name = '{region}'), '{country}', {scm_ready}, '{timezone}');\n"
                     .format(
                         region=region,
                         country=country,
                         scm_ready=scm_ready_str,
                         timezone=timezone
        ))

    # If we didn't map all the countries that are scm ready, something is wrong
    if len(country_scm_ready["items"]) != 0:
        print("ERROR: We are missing the following countries in the country/region mapping:")
        for country in country_scm_ready["items"]:
            print("   {}".format(country_scm_ready["items"][i]["sapeligiblecountry"]))
        sys.exit(1)
