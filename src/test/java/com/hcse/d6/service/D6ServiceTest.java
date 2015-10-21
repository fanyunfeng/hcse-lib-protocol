package com.hcse.d6.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcse.cache.service.CacheServiceTest;
import com.hcse.d6.protocol.factory.D6ResponseMessageFactory;
import com.hcse.d6.protocol.factory.D6ResponseMessageFactory4Client;
import com.hcse.d6.protocol.factory.D6ResponseMessageFactory4Logistic;
import com.hcse.d6.protocol.message.D6RequestMessage;
import com.hcse.d6.protocol.message.D6RequestMessageDoc;
import com.hcse.d6.protocol.message.D6ResponseMessage;
import com.hcse.d6.service.DataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:spring-client.xml" })
public class D6ServiceTest {
    protected static final Logger logger = Logger.getLogger(CacheServiceTest.class);

    @Autowired
    private DataService service;

    @Test
    public void testSearch() {
        try {
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[F][DG:PR:1][P][DG:UT:0]";

            D6RequestMessage request = new D6RequestMessage(searchStr);

            request.setDocsCount(3);

            {
                D6RequestMessageDoc doc = request.getDocById(0);
                doc.setMachineId(11);
                doc.setMd5Lite("63e74ddcce8b1b6a");
                doc.setIndentCount(0XAAAAAAAA);
                doc.setWeight(0XBBBBBBBBBBBBBBBBL);
            }

            {
                D6RequestMessageDoc doc = request.getDocById(1);
                doc.setMachineId(11);
                doc.setMd5Lite("64b18687d1c562be");
                doc.setIndentCount(0XCCCCCCCC);
                doc.setWeight(0XDDDDDDDDDDDDDDDDL);
            }

            {
                D6RequestMessageDoc doc = request.getDocById(2);
                doc.setMachineId(11);
                doc.setMd5Lite("3F8AB4D7AC71B748");
                doc.setIndentCount(0XEEEEEEEE);
                doc.setWeight(0XFFFFFFFFFFFFFFFFL);
            }

            request.setServiceAddress("data://192.168.246.14:3000");

            long start = System.currentTimeMillis();
            D6ResponseMessage response = service.search(request, new D6ResponseMessageFactory());

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
    public void testSearch4Client() {
        try {
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[F][DG:PR:1][P][DG:UT:0]";

            D6RequestMessage request = new D6RequestMessage(searchStr);

            request.setDocsCount(3);

            {
                D6RequestMessageDoc doc = request.getDocById(0);
                doc.setMachineId(11);
                doc.setMd5Lite("63e74ddcce8b1b6a");
                doc.setIndentCount(0XAAAAAAAA);
                doc.setWeight(0XBBBBBBBBBBBBBBBBL);
            }

            {
                D6RequestMessageDoc doc = request.getDocById(1);
                doc.setMachineId(11);
                doc.setMd5Lite("64b18687d1c562be");
                doc.setIndentCount(0XCCCCCCCC);
                doc.setWeight(0XDDDDDDDDDDDDDDDDL);
            }

            {
                D6RequestMessageDoc doc = request.getDocById(2);
                doc.setMachineId(11);
                doc.setMd5Lite("3F8AB4D7AC71B748");
                doc.setIndentCount(0XEEEEEEEE);
                doc.setWeight(0XFFFFFFFFFFFFFFFFL);
            }

            request.setServiceAddress("data://192.168.246.14:3000");

            long start = System.currentTimeMillis();
            D6ResponseMessage response = service.search(request, new D6ResponseMessageFactory4Client());

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
    public void testSearch4Logistic() {
        try {
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[F][DG:PR:1][P][DG:UT:0]";

            D6RequestMessage request = new D6RequestMessage(searchStr);

            request.setDocsCount(3);

            {
                D6RequestMessageDoc doc = request.getDocById(0);
                doc.setMachineId(11);
                doc.setMd5Lite("63e74ddcce8b1b6a");
                doc.setIndentCount(0XAAAAAAAA);
                doc.setWeight(0XBBBBBBBBBBBBBBBBL);
            }

            {
                D6RequestMessageDoc doc = request.getDocById(1);
                doc.setMachineId(11);
                doc.setMd5Lite("64b18687d1c562be");
                doc.setIndentCount(0XCCCCCCCC);
                doc.setWeight(0XDDDDDDDDDDDDDDDDL);
            }

            {
                D6RequestMessageDoc doc = request.getDocById(2);
                doc.setMachineId(11);
                doc.setMd5Lite("3F8AB4D7AC71B748");
                doc.setIndentCount(0XEEEEEEEE);
                doc.setWeight(0XFFFFFFFFFFFFFFFFL);
            }

            request.setServiceAddress("data://192.168.246.14:3000");

            long start = System.currentTimeMillis();
            D6ResponseMessage response = service.search(request, new D6ResponseMessageFactory4Logistic());

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
