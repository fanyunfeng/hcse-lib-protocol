package com.hcse.d2.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcse.cache.service.CacheServiceTest;
import com.hcse.d2.protocol.codec.D2ClientCodecFactory;
import com.hcse.d2.protocol.factory.D2ResponseMessageFactory;
import com.hcse.d2.protocol.message.D2RequestMessage;
import com.hcse.d2.protocol.message.D2ResponseMessage;
import com.hcse.d2.service.IndexService;

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
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[F][DG:PR:1][P][DG:UT:0]";

            D2RequestMessage request = new D2RequestMessage("127.0.0.1", 1, 20, 0, searchStr);

            request.setIndent(false);
            request.setSortMethod(601);

            request.setServiceAddress("index://192.168.244.250:3000");

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
