package com.example.demo;

import javax.swing.text.TableView.TableRow;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAckReturnCode;

@RestController
@RequestMapping("/api")
public class MainController {

    private static final String MQTT_BROKER_HOST = "test.mosquitto.org";  // 使用公共 MQTT Broker
    private static final int MQTT_BROKER_PORT = 1883;
    private static final String MQTT_TOPIC = "stevenhaveATopic";
    private Mqtt3AsyncClient client;
    //建構子
    
        private void connectToMqttBroker() {
            client.connectWith()
                    .send()
                    .whenComplete((connAck, throwable) -> {
                        if (throwable != null) {
                            System.err.println("出現錯誤: "+throwable);
                        } else if (connAck.getReturnCode() == Mqtt3ConnAckReturnCode.SUCCESS) {
                            System.out.println("MQTT 連接成功");
                            subscribeToTopic();  // 在成功連接後訂閱主題
                        } else {
                            
                            System.err.println("接收錯誤: "+connAck.getReturnCode());
                        }
                    });
        }
        private void subscribeToTopic() {
            client.subscribeWith()
                    .topicFilter(MQTT_TOPIC)
                    .callback(publish -> {
                        String message = new String(publish.getPayloadAsBytes());
                        System.out.println("收到: "+message);
                        parseAndDisplayData(message);
                    })
                    .send();
        }

        private void parseAndDisplayData(String jsonData) {
        try {
            // 假設返回的是 JSON 數組
//            JSONArray jsonArray = new JSONArray(jsonData);

//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
            
            System.out.println("jsonData"+jsonData);

//            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @PostMapping("/post")
    public String feedback(@RequestBody String request){
        client = MqttClient.builder()
                    .useMqttVersion3()
                    .serverHost(MQTT_BROKER_HOST)
                    .serverPort(MQTT_BROKER_PORT)
                    .buildAsync();
    
            connectToMqttBroker();
            
        return request;
    }
}
