package codes.mohitkv.portfolio.data.repositories;

import codes.mohitkv.portfolio.PortfolioApplication;
import codes.mohitkv.portfolio.data.models.BlogPost;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PortfolioApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:3966/", "amazon.aws.accesskey=key", "amazon.aws.secretkey=key2" })
public class BlogPostRepositoryIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    BlogPostRepository repository;

    private static final String TITLE = "Test Blog post";
    private static final String CONTENT = "Any content";
    private static String DATE_TIME_STRING;

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(BlogPost.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);


        // your code here...

        dynamoDBMapper.batchDelete((List<BlogPost>)repository.findAll());
    }

    @Test
    public void dynamoDBTestCase() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        DATE_TIME_STRING = dtf.format(now);
        BlogPost blogPost = new BlogPost(TITLE, CONTENT, DATE_TIME_STRING);
        repository.save(blogPost);

        List<BlogPost> result = (List<BlogPost>) repository.findAll();
        assertTrue("Post found!", result.size()>0);
        assertTrue("Content is same", result.get(0).getContent().equals(CONTENT));
    }
}
