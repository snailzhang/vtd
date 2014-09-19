package com.esd.common.captcha;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 
 * 验证 码
 * 
 * @author zhangjianzong 2013-06-07
 * 
 */
public class CaptchaServiceSingleton {

    private CaptchaServiceSingleton() {}

    private static ImageCaptchaService instance = new DefaultManageableImageCaptchaService(
            new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);

    // private static ImageCaptchaService instance=new DefaultManageableImageCaptchaService();
    public static ImageCaptchaService getInstance() {
        return instance;
    }
 }
