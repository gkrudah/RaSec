package com.rasec.server.core;

import com.rasec.server.config.DeviceConfig;
import com.rasec.server.model.Device;
import com.rasec.server.model.PhotoGroup;
import com.rasec.server.model.Photos;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
public class ApiController {

    @GetMapping("RaSec")
    public ModelAndView rasec(){
        ModelAndView mv = new ModelAndView();
        List<String> listTest = new ArrayList<String>();
        listTest.add("test1");
        listTest.add("test3");
        listTest.add("test2");

        mv.addObject("listTest", listTest);
        mv.addObject("ObjectTest", "test!!");
        mv.setViewName("rasec");
        return mv;
    }

    @GetMapping("RaSec/devices/**")
    public ModelAndView getDevices() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("device", DeviceConfig.device);
        mv.setViewName("state/device");
        return mv;
    }

    @GetMapping("RaSec/videos/{videoId}")
    public ModelAndView getStreaming(@PathVariable String videoId) throws UnsupportedEncodingException{
        return new ModelAndView("streamView", "movieName", videoId);
    }

    @GetMapping("RaSec/photos/{photoID}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String photoID) throws IOException{
        log.info(photoID);
        String path = "./photos/" + photoID;
        FileInputStream fileStream = null;
        /*InputStream in = new FileInputStream(new File(path));
        //fileStream = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(in);
        byte[] image = new byte[];
        */
        byte[] image = Files.readAllBytes(Paths.get(path));
        // Files.write(Paths.get("photos"), imageByte);
        // byte[] image = ImageIO.read(new File(path);
        /*
        BufferedImage bImage = ImageIO.read(new File("./photos/" + photoID));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", baos);
        byte[] image = baos.toByteArray();
         */
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("RaSec/photos")
    public ModelAndView getPhotoGroup(){
        File[] files = new File("./photos/").listFiles();
        List<String> results = new ArrayList<String>();
        for (File file : files){
            if (file.isFile()){
                log.info(file.getName());
                results.add("photos/" + file.getName());
            }
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("results", results);
        mv.addObject("ObjectTest", "test!!");
        mv.setViewName("photos");
        return mv;
    }

    @GetMapping("RaSec/videos")
    public ModelAndView getVideos(){
        File[] files = new File("./videos/").listFiles();
        List<String> results = new ArrayList<String>();
        for (File file : files){
            if (file.isFile()){
                log.info(file.getName());
                results.add("videos/" + file.getName());
            }
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("results",results);
        mv.setViewName("videos");
        return mv;
    }

    /*
    @RequestMapping(value = "RaSec/device")
    public ModelAndView stateCamera(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String deviceId = request.getParameter("deviceId");
        log.info("deviceId:???{}",deviceId);
        return mv;
    }*/
    /*@RequestMapping(value = "RaSec/photos", method = RequestMethod.PUT)
    public void addPhoto(HttpServletRequest request){
        String name = request.getParameter("name");
        log.info(name);
        String imageString = request.getParameter("imageByte");
        log.info(imageString);
    }*/

    @PostMapping("RaSec/device")
    public ModelAndView postDevice(@ModelAttribute Device device) {
        log.info(device.toString());
        ModelAndView mv = new ModelAndView();
        if (DeviceConfig.device.getDeviceId().equals(device.getDeviceId())){
            log.info("deviceID same??");
            if (device.getBuzzerState() != null){
                log.info("buzzerState change");
                DeviceConfig.device.setBuzzerState(device.getBuzzerState());
                DeviceConfig.device.observeResourceChanged();
            }
            if (device.getCamState() != null) {
                DeviceConfig.device.setCamState(device.getCamState());
            }
        }
        else{
            throw new NotFoundException(device.getDeviceId() + " does not exists");
        }
        mv.addObject("device",DeviceConfig.device);
        mv.setViewName("state/stateChange");
        return mv;
    }

    //@ModelAttribute
    @PutMapping("RaSec/devices")
    public ModelAndView putDevice( Device device) {
        return postDevice(device);
    }

    @PutMapping("RaSec/photos")
    public void addPhoto(@RequestBody Photos photo){
        log.info("RaSec/photos put request");
        log.info(photo.getName());
        String imageString = photo.getImageByte();
        imageString = imageString.substring(2,imageString.length()-1);
        byte[] imageByte;
        try{
            if(photo.getImageByte() == null) {
                log.info("image null");
            }
            log.info(imageString);
            Base64.Decoder decoder = Base64.getDecoder();
            imageByte = decoder.decode(imageString);

            // imageByte = decoder.decode(photo.getImageByte());
            // imageByte = photo.getImageByte().getBytes();
            // ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);
            // BufferedImage bufferedImage = ImageIO.read(inputStream);
            Files.write(Paths.get("./photos/"+ photo.getName() + ".jpg"), imageByte);
            //ImageIO.write(bufferedImage, "jpg", new File("photos/" + photo.getName() + ".jpg"));
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @PutMapping("RaSec/videos")
    public void addVideo(){

    }
}
