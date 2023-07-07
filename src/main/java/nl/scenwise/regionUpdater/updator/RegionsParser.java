package nl.scenwise.regionUpdater.updator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.scenwise.regionUpdater.data.Regions;
import nl.scenwise.regionUpdater.service.RedisStreamingUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
This accepts a geojson from the resource folder
 */
@Slf4j
public class RegionsParser {
    private ObjectMapper om;
    private RedissonClient redisClient;


    public RegionsParser(){initiate();}

    private void initiate(){
        this.om = new ObjectMapper();
        this.redisClient = RedisStreamingUtil.getInstance().getRedisClient();
    }

    private Regions getRegions(String fileNameLocal) {
        Regions regions = null;
        try {
            InputStream inputStream = RegionsParser.class.getClassLoader().getResourceAsStream(fileNameLocal);
            if (inputStream != null) {
                regions = this.om.readValue(inputStream, Regions.class);

                // Use the parsed geometry object as needed
            } else {
                log.warn("Could not find the GeoJSON file: " + fileNameLocal);
            }
        } catch (IOException e) {
            log.warn("Error reading the GeoJSON file: " + e.getMessage());
        }
        return regions;
    }

    public void updateRegions(String key, String fileName) {
        RBucket<Map<String, ArrayList<ArrayList<Double>>>> regionPolygonBuckets = this.redisClient.getBucket(key);
        Map<String, ArrayList<ArrayList<Double>>> regionPolygons = new HashMap<>();
        getRegions(fileName).features.forEach(currentRegion -> {
            //TODO: In the given file, coordinates section is a three dimensional list. With eye could not see more than one list in a list of list of coordinates. See why it exists in the first place and double check.
            regionPolygons.put(currentRegion.properties.label, currentRegion.geometry.coordinates.get(0));
        });
        regionPolygonBuckets.set(regionPolygons);

        // Wait 5 seconds as it can time for redis streams to update it on their end
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
