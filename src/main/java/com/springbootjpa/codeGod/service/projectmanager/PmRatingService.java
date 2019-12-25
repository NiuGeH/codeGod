package com.springbootjpa.codeGod.service.projectmanager;

import com.springbootjpa.codeGod.codeException.CodeGodException;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface PmRatingService {


    boolean saveRating(MultipartFile[] performance, MultipartFile photos, HashMap<String,String> map) throws CodeGodException;




}
