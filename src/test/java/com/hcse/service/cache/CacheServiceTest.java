package com.hcse.service.cache;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcse.protocol.cache.codec.CacheClientCodecFactory;
import com.hcse.protocol.cache.factory.CacheResponseMessageFactory;
import com.hcse.protocol.cache.factory.CacheResponseMessageFactory4Logistics;
import com.hcse.protocol.cache.message.CacheRequestMessage;
import com.hcse.protocol.cache.message.CacheResponseMessage;
import com.hcse.service.cache.CacheService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:spring-client.xml" })
public class CacheServiceTest extends Thread {
    protected static final Logger logger = Logger.getLogger(CacheServiceTest.class);

    @Autowired
    private CacheService service;

    @Test
    public void testLogistics() {
        try {
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[P][DG:UT:0][SYSNAME:wlorder][BUSSNAME:pc]";

            CacheRequestMessage reqMessage = new CacheRequestMessage("127.0.0.1", 1, 10, 0, searchStr);

            reqMessage.setIndent(0);
            reqMessage.setSortMethod(601);
            reqMessage.setFreshFlag(1);

            // reqMessage.setServiceAddress("cache://192.168.244.109:5555");
            reqMessage.setServiceAddress("cache://192.168.244.250:5555");
            // reqMessage.setServiceAddress("cache://192.168.60.22:5555");
            // reqMessage.setServiceAddress("cache://192.168.60.81:5555");

            long start = System.currentTimeMillis();
            CacheResponseMessage response = service.search(reqMessage, new CacheClientCodecFactory(new CacheResponseMessageFactory4Logistics()));

            long userd = System.currentTimeMillis() - start;
            logger.info("time used:" + userd);

            if (response != null) {
                response.dump();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearch() {
        try {
            String searchStr = "[S](([TX:TI:最便宜卫衣]))&([CL:CC:001])[K][最便宜卫衣][FC:1][REQ_ID:3f903c3a63aa4307bdf68ad12365519d][SYSNAME:search][BUSSNAME:ls][TYPE:CACHE]";
            //String searchStr = "[S](([TX:TD:47291559]))&([CL:CC:001])[FC:1][REQ_ID:e5b28e9bd0714df6386784f1b372bf3d]";
            // String searchStr =
            // "[S](([TX:TI:收割机]))&([CL:CC:001])[K][收割机][FC:1][SYSNAME:search][BUSSNAME:ls][TYPE:CACHE]";
            // String searchStr =
            // "[S](([TX:TI:山地车]))&([CL:CC:001])[K][山地车][FC:1][BW:BR:0][SYSNAME:][BUSSNAME:][TYPE:CACHE]";

            CacheRequestMessage request = new CacheRequestMessage("127.0.0.1", 1, 10, 0, searchStr);

            request.setIndent(0);
            request.setSortMethod(1);
            request.setFreshFlag(1);
            request.setStatisic(1024);

            // request.setServiceAddress("cache://192.168.60.22:5555");
            request.setServiceAddress("cache://192.168.45.51:5555");

            long start = System.currentTimeMillis();
            CacheResponseMessage response = service.search(request, new CacheClientCodecFactory(new CacheResponseMessageFactory()));

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
