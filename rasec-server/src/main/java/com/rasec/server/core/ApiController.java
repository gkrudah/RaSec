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
import java.util.ArrayList;
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
    public Device getDevices() {
        return DeviceConfig.device;
    }

    @GetMapping("RaSec/videos/{movieName}")
    public ModelAndView getStreaming(@PathVariable String movieName) throws UnsupportedEncodingException{
        return new ModelAndView("streamView", "movieName", movieName);
    }

    @GetMapping("RaSec/photos/{photoID}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String photoID) throws IOException{
        log.info(photoID);
        BufferedImage bImage = ImageIO.read(new File("./photos/" + photoID));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", baos);
        byte[] image = baos.toByteArray();
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

    @PostMapping("RaSec/devices")
    public Device postDevice(@RequestBody Device device) {
        log.info(device.toString());
        if (DeviceConfig.device.getDeviceId().equals(device.getDeviceId())){
            if (device.getBuzzerState() != null){
                DeviceConfig.device.setBuzzerState(device.getBuzzerState());
            }
            if (device.getCamState() != null) {
                DeviceConfig.device.setCamState(device.getCamState());
            }
        }
        else{
            throw new NotFoundException(device.getDeviceId() + " does not exists");
        }
        return DeviceConfig.device;
    }

    @PutMapping("RaSec/devices")
    public Device putDevice(@RequestBody Device device) {
        return postDevice(device);
    }

    @PutMapping("RaSec/photos")
    public void addPhoto(@RequestBody Photos photo){
        try{
            ByteArrayInputStream inputStream = new ByteArrayInputStream(photo.getImageByte());
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            ImageIO.write(bufferedImage, "jpg", new File("photos/" + photo.getName()));
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @PutMapping("RaSec/videos")
    public void addVideo(){

    }
}
