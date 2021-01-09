package com.alibaba.health.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.health.constant.MessageConstant;
import com.alibaba.health.entity.PageResult;
import com.alibaba.health.entity.QueryPageBean;
import com.alibaba.health.entity.Result;
import com.alibaba.health.pojo.Setmeal;
import com.alibaba.health.service.SetMealService;
import com.alibaba.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetMealService setMealService;

    /**
     * 上传图片
     * @param imgFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //1获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2.获取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3. 生成唯一id,
        String uniqueId = UUID.randomUUID().toString();
        //4. 拼接唯一文件名
        String filename = uniqueId + suffix ;
        try {
            //5. 调用7牛工具上传图片
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),filename);
            //6. 构建返回的数据
            /*
             {
                imgName: 图片名 , 补全formData.img
                domain: 七牛的域名 图片回显imageUrl = domain+图片名
            }
             */
            Map<String,String>map = new HashMap<String,String>(2);
            map.put("imgName",filename);
            map.put("domain",QiNiuUtils.DOMAIN);
            //7. 放到result里，再返回给页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,map);
        } catch (IOException e) {
            log.error("图片上传失败",e);
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setMealService.add(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult<Setmeal> pageResult =  setMealService.findPage(queryPageBean);
       return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }
}
