package com.hcse.service.d6;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hcse.protocol.d6.codec.D6ClientCodecFactory;
import com.hcse.protocol.d6.factory.D6ResponseMessageFactory;
import com.hcse.protocol.d6.factory.D6ResponseMessageFactory4Client;
import com.hcse.protocol.d6.factory.D6ResponseMessageFactory4JsonClient;
import com.hcse.protocol.d6.factory.D6ResponseMessageFactory4Logistic;
import com.hcse.protocol.d6.message.D6RequestMessage;
import com.hcse.protocol.d6.message.D6RequestMessageDoc;
import com.hcse.protocol.d6.message.D6RequestMessageV1;
import com.hcse.protocol.d6.message.D6RequestMessageV3;
import com.hcse.protocol.d6.message.D6ResponseMessage;
import com.hcse.service.cache.CacheServiceTest;
import com.hcse.service.d6.DataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = { "classpath:spring-client.xml" })
public class D6ServiceTest {
    protected static final Logger logger = Logger.getLogger(CacheServiceTest.class);

    @Autowired
    private DataService service;

    @Test
    public void testSearchV1() {
        try {
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[F][DG:PR:1][P][DG:UT:0]";

            D6RequestMessage request = new D6RequestMessageV1(searchStr);

            request.setDocsCount(2);

            {
                D6RequestMessageDoc doc = request.getDocById(0);
                doc.setMachineId(11);
                doc.setMd5Lite("69549A15E5BB908");
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

            request.setServiceAddress("data://192.168.34.239:3000");

            long start = System.currentTimeMillis();
            D6ResponseMessage response = service.search(request, new D6ClientCodecFactory(
                    new D6ResponseMessageFactory()));

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
    public void testSearchV3() {
        try {
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[F][DG:PR:1][P][DG:UT:0]";

            D6RequestMessage request = new D6RequestMessageV3(searchStr);

            request.setDocsCount(2);

            {
                D6RequestMessageDoc doc = request.getDocById(0);
                doc.setMachineId(11);
                doc.setMd5Lite("F6B33DBC35260D72");
                doc.setIndentCount(0XAAAAAAAA);
                doc.setWeight(0XBBBBBBBBBBBBBBBBL);
            }

            {
                D6RequestMessageDoc doc = request.getDocById(1);
                doc.setMachineId(11);
                doc.setMd5Lite("5BD5577A89AA2B26");
                doc.setIndentCount(0XCCCCCCCC);
                doc.setWeight(0XDDDDDDDDDDDDDDDDL);
            }

            request.setServiceAddress("data://192.168.34.239:3000");

            long start = System.currentTimeMillis();
            D6ResponseMessage response = service.search(request, new D6ClientCodecFactory(
                    new D6ResponseMessageFactory4JsonClient()));

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
            String searchStr = "[S](([TX:TP:中国:北京:北京市-中国:天津:天津市]))&([CL:CC:080])[F][DG:PR:1][P][DG:UT:0]";

            D6RequestMessage request = new D6RequestMessage(searchStr);

            request.setDocsCount(3);

            {
                D6RequestMessageDoc doc = request.getDocById(0);
                doc.setMachineId(11);
                doc.setMd5Lite("69549A15E5BB908");
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

            request.setServiceAddress("data://192.168.34.239:3000");

            long start = System.currentTimeMillis();
            D6ResponseMessage response = service.search(request, new D6ClientCodecFactory(
                    new D6ResponseMessageFactory()));

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
            D6ResponseMessage response = service.search(request, new D6ClientCodecFactory(
                    new D6ResponseMessageFactory4Client()));

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
            D6ResponseMessage response = service.search(request, new D6ClientCodecFactory(
                    new D6ResponseMessageFactory4Logistic()));

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
