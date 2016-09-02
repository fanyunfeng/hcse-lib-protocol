package com.hcse.service.d2;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcse.protocol.d2.codec.D2ClientCodecFactory;
import com.hcse.protocol.d2.factory.D2ResponseMessageFactory;
import com.hcse.protocol.d2.message.D2RequestMessage;
import com.hcse.protocol.d2.message.D2ResponseMessage;
import com.hcse.service.cache.CacheServiceTest;
import com.hcse.service.d2.IndexService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:spring-client.xml" })
public class D2ServiceTest {
    protected static final Logger logger = Logger.getLogger(CacheServiceTest.class);

    @Autowired
    private IndexService service;

    @Test
    public void testSearch() {
        try {
            String searchStr = "[S](([TX:TI:MP3]))&([CL:CC:001])";

            D2RequestMessage request = new D2RequestMessage("127.0.0.1", 1, 15, 0, searchStr);

            request.setIndent(false);
            request.setSort(1);

            request.setServiceAddress("index://192.168.46.104:3000");

            long start = System.currentTimeMillis();
            D2ResponseMessage response = service.search(request, new D2ClientCodecFactory(
                    new D2ResponseMessageFactory()));

            long userd = System.currentTimeMillis() - start;
            logger.info("time used:" + userd);

            if (response != null) {
                response.dump();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
