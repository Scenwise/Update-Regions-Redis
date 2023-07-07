import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import nl.scenwise.regionUpdater.service.RedisStreamingUtil;
import nl.scenwise.regionUpdater.updator.RegionsParser;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.GeoPosition;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.logging.Logger;

public class TestAllRegions {
    private RedissonClient redissonClient;
    private Logger log;
    private String regionBucketName;
    private String geoJsonRegionPolygonFileName;

    @Before
    public void setUp(){
        try {
            redissonClient = RedisStreamingUtil.getInstance().getRedisClient();
        }catch(Exception e) {
            System.out.println("Error occured: " + e.getMessage() +"\nAttempting to connect again");
            this.redissonClient = RedisStreamingUtil.getInstance().getRedisClient();}
        this.log = Logger.getLogger(TestAllRegions.class.getSimpleName());
        this.regionBucketName = "region:polygons";
        this.geoJsonRegionPolygonFileName = "login-regions.json";

    }

    @Test
    public void refreshRegionPolygons() {
        RBucket<Map<String, ArrayList<ArrayList<Double>>>> regionBucket = this.redissonClient.getBucket(this.regionBucketName);
        if(regionBucket.isExists()) {
            if(regionBucket.delete()) log.info("Bucket deleted successfully. Reloading coordinates...");
        }
        else {
            new RegionsParser().updateRegions(regionBucketName, geoJsonRegionPolygonFileName);
            log.info("Bucket added.");
        }
    }

    @Test
    public void printAllRegionCoordinates(){
        RBucket<Map<String, ArrayList<ArrayList<Double>>>> regionBucket = this.redissonClient.getBucket(this.regionBucketName);
        regionBucket.get().forEach((currentRegion, coordinates) -> {
            System.out.println("\nCurrent region: " + currentRegion + " | Coordinates: \n");
            coordinates.forEach(currentCoordinates -> System.out.println(currentCoordinates.get(0) + " , " + currentCoordinates.get(1)));
        });
    }

}
