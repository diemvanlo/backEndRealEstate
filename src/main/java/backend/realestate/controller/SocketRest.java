package backend.realestate.controller;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/socket")
@CrossOrigin
public class SocketRest {
    @Autowired
    private SimpMessagingTemplate simpleMessage;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity useSimpleRest(@RequestBody Map<String, String> message) {
        if(message.containsKey("message")){
            if(message.containsKey("toId") && message.get("toId")!=null && !message.get("toId").equals("")){
                this.simpleMessage.convertAndSend("/socket-publisher/"+message.get("toId"),message);
                this.simpleMessage.convertAndSend("/socket-publisher/"+message.get("fromId"),message);
            }else{
                this.simpleMessage.convertAndSend("/socket-publisher",message);
            }
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @MessageMapping("/send/message")
    public Map<String, String> useSocketCommunication(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> messageConverted = null;
        try {
            messageConverted = mapper.readValue(message, Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (messageConverted != null) {
            if (messageConverted.containsKey("toId") && messageConverted.get("toId").isEmpty()) {
                this.simpleMessage.convertAndSend("/socket-publisher/" + messageConverted.get("toId"), messageConverted);
                this.simpleMessage.convertAndSend("/socket-publisher/" + messageConverted.get("fromId"), message);
            } else {
                this.simpleMessage.convertAndSend("/socket-publisher", messageConverted);
            }
        }
        return messageConverted;
    }
}
