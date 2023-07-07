package nl.scenwise.regionUpdater;

import lombok.extern.slf4j.Slf4j;
import nl.scenwise.regionUpdater.service.RedisStreamingUtil;
import nl.scenwise.regionUpdater.updator.RegionsParser;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
public class RegionUpdaterRedis {
    private RedissonClient redissonClient;
    private String regionBucketName;
    private String geoJsonRegionPolygonFileName;

    public RegionUpdaterRedis() {initliase();}
    public void initliase() {
        this.redissonClient = RedisStreamingUtil.getInstance().getRedisClient();
        this.regionBucketName = "region:polygons";
        this.geoJsonRegionPolygonFileName = "login-regions.json";
    }

    public void refreshRegionPolygons() {
        RBucket<Map<String, ArrayList<ArrayList<Double>>>> regionBucket = this.redissonClient.getBucket(this.regionBucketName);
        if(regionBucket.isExists()) {
            if(regionBucket.delete()) log.info("Bucket deleted successfully. Reloading coordinates...");
        }
        new RegionsParser().updateRegions(regionBucketName, geoJsonRegionPolygonFileName);
        log.info("Bucket added.");
    }
    public void printAllRegionCoordinates(){
        RBucket<Map<String, ArrayList<ArrayList<Double>>>> regionBucket = this.redissonClient.getBucket(this.regionBucketName);
        regionBucket.get().forEach((currentRegion, coordinates) -> {
            System.out.println("\nCurrent region: " + currentRegion + " | Coordinates: \n");
            coordinates.forEach(currentCoordinates -> System.out.println(currentCoordinates.get(0) + " , " + currentCoordinates.get(1)));
        });
    }

}
