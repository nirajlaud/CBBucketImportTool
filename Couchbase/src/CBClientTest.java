import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import com.couchbase.client.java.query.N1qlQuery;



public class CBClientTest {

	public static final String BUCKET = "beer-sample"; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Cluster cluster = Cluster.connect("localhost?timeout.kvTimeout=200s", "get", "12345678");
//		Bucket bucket = cluster.bucket("travel-sample");
//		Collection collection = bucket.defaultCollection();
		CouchbaseEnvironment env = DefaultCouchbaseEnvironment
			    .builder()
			    .sslEnabled(true)
			    .sslKeystoreFile(".keystore")
			    .sslKeystorePassword("changeit")
			    .build();
		
		final String CB_HOST = "localhost?timeout.kvTimeout=200s";
		final String CB_UN = "get";
		final String CB_PW = "12345678";
		
		
        Cluster cluster = CouchbaseCluster.create(env, CB_HOST);
        cluster.authenticate(CB_UN, CB_PW);
        Bucket bucket = cluster.openBucket(BUCKET);

        Bucket bucket1 = cluster.openBucket("uncompressed");
        
        CBClientTest test = new CBClientTest();
        test.getAll(bucket, bucket1);
	}

	
	
	public void getAll(final Bucket bucket, final Bucket bucket1) {
		//delete from uncompressed where meta().id > 0 and meta().expiration > 0
	    String queryStr = "SELECT meta().id as dockey,* FROM `"+BUCKET+"` order by meta().id asc";
	    bucket.async().query(N1qlQuery.simple(queryStr))
	            .flatMap(AsyncN1qlQueryResult::rows)
	            .toBlocking()
	            .forEach(row ->  
	            	{ 
	            		System.out.println("hello " + row.value());
	            		bucket1.insert(JsonDocument.create(row.value().getString("dockey"), row.value().getObject(BUCKET)));
	            	}
	    );
	}	
}
