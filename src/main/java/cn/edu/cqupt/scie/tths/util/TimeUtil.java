/**
 * 
 */
package cn.edu.cqupt.scie.tths.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */

public class TimeUtil {
    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    public static String getNowTime2(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    public static Long getTimestamp() {
	    return System.currentTimeMillis()/1000;
    }

    public static void main(String[] args){
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(sdf.format(date));
//        String a = "\"鲍宁海\"<baonh@cqupt.edu.cn>;\"柴蓉\"<chairong@cqupt.edu.cn>;\"曹建玲\"<caojl@cqupt.edu.cn>;\"曹傧\"<caobin@cqupt.edu.cn>;\"蔡丽\"<caili@cqupt.edu.cn>;\"陈昌川\"<chencc@cqupt.edu.cn>;\"陈发堂\"<chenft@cqupt.edu.cn>;\"陈莉\"<chenl@cqupt.edu.cn>;\"陈英\"<chenying@cqupt.edu.cn>;\"陈培然\"<chenpr@cqupt.edu.cn>;\"陈善学\"<chensx@cqupt.edu.cn>;\"陈文星\"<chenwx1@cqupt.edu.cn>;\"代少升\"<daiss@cqupt.edu.cn>;\"崔太平\"<cuitp@cqupt.edu.cn>;\"戴翠琴\"<daicq@cqupt.edu.cn>;\"邓炳光\"<dengbg@cqupt.edu.cn>;\"段洁\"<duanjie@cqupt.edu.cn>;\"范馨月\"<fanxy@cqupt.edu.cn>;\"高陈强\"<gaocq@cqupt.edu.cn>;\"甘臣权\"<gancq@cqupt.edu.cn>;\"龚璞\"<gongpu@cqupt.edu.cn>;\"郭晓金\"<guoxj@cqupt.edu.cn>;\"刘宏清\"<hongqingliu@cqupt.edu.cn>;\"何维\"<hewei@cqupt.edu.cn>;\"黄晓舸\"<huangxg@cqupt.edu.cn>;\"黄宏程\"<huanghc@cqupt.edu.cn>;\"黄明华\"<huangmh@cqupt.edu.cn>;\"黄胜\"<huangs@cqupt.edu.cn>;\"胡昊南\"<huhn@cqupt.edu.cn>;\"胡敏\"<humin@cqupt.edu.cn>;\"胡庆\"<huqing@cqupt.edu.cn>;\"景小荣\"<jingxr@cqupt.edu.cn>;\"蒋莹\"<jiangying@cqupt.edu.cn>;\"蒋青\"<jiangq@cqupt.edu.cn>;\"雷维嘉\"<leiwj@cqupt.edu.cn>;\"梁燕\"<liangyan@cqupt.edu.cn>;\"雷芳\"<leifang1@cqupt.edu.cn>;\"雷宏江\"<leihj@cqupt.edu.cn>;\"廖述平\"<liaosp@cqupt.edu.cn>;\"廖希\"<liaoxi@cqupt.edu.cn>;\"李国权\"<ligq@cqupt.edu.cn>;\"李玲霞\"<lilx@cqupt.edu.cn>;\"李季碧\"<lijb@cqupt.edu.cn>;\"李秋俊\"<liqj@cqupt.edu.cn>;\"李强\"<liqiang@cqupt.edu.cn>;\"刘楚川\"<liucc@cqupt.edu.cn>;\"刘畅\"<liuchang@cqupt.edu.cn>;\"刘景刚\"<liujg@cqupt.edu.cn>;\"刘焕淋\"<liuhl@cqupt.edu.cn>;\"刘劲松\"<liujs@cqupt.edu.cn>;\"刘科征\"<liukz@cqupt.edu.cn>;\"刘鸿\"<liuhong@cqupt.edu.cn>;\"刘亮\"<liuliang@cqupt.edu.cn>;\"刘媛妮\"<liuyn@cqupt.edu.cn>;\"刘乔寿\"<liuqs@cqupt.edu.cn>;\"刘裔\"<liuyi@cqupt.edu.cn>;\"刘小莉\"<liuxl@cqupt.edu.cn>;\"李小文\"<lixw@cqupt.edu.cn>;\"刘占军\"<liuzj@cqupt.edu.cn>;\"李兆玉\"<lizhaoyu@cqupt.edu.cn>;\"龙恳\"<longken@cqupt.edu.cn>;\"李永刚\"<lyg@cqupt.edu.cn>;\"罗文丰\"<luowf@cqupt.edu.cn>;\"罗忠涛\"<luozt@cqupt.edu.cn>;\"罗臻\"<luozhen@cqupt.edu.cn>;\"聂伟\"<niewei@cqupt.edu.cn>;\"明艳\"<mingyan@cqupt.edu.cn>;\"泮凯\"<pankai@cqupt.edu.cn>;\"裴二荣\"<peier@cqupt.edu.cn>;\"蒲巧林\"<puql@cqupt.edu.cn>;\"蒲旭敏\"<puxm@cqupt.edu.cn>;\"漆晶\"<qijing@cqupt.edu.cn>;\"师黎明\"<shilm@cqupt.edu.cn>;\"史朝翔\"<shicx@cqupt.edu.cn>;\"任智\"<renzhi@cqupt.edu.cn>;\"邵凯\"<shaokai@cqupt.edu.cn>;\"邵羽\"<shaoyu@cqupt.edu.cn>;\"申滨\"<shenbin@cqupt.edu.cn>;\"舒娜\"<shuna@cqupt.edu.cn>;\"宋铁成\"<songtc@cqupt.edu.cn>;\"唐建华\"<tangjh@cqupt.edu.cn>;\"唐红\"<tanghong@cqupt.edu.cn>;\"唐宏\"<tangh@cqupt.edu.cn>;\"腾欢\"<tenghuan@cqupt.edu.cn>;\"谭钦红\"<tanqh@cqupt.edu.cn>;\"唐伦\"<tangl@cqupt.edu.cn>;\"谭歆\"<tanxin@cqupt.edu.cn>;\"陶洋\"<taoyang@cqupt.edu.cn>;\"田增山\"<tianzs@cqupt.edu.cn>;\"王丹\"<wangdan@cqupt.edu.cn>;\"王华华\"<wanghh@cqupt.edu.cn>;\"王俊\"<wangjun@cqupt.edu.cn>;\"王洋\"<wangyang@cqupt.edu.cn>;\"王英\"<wangying@cqupt.edu.cn>;\"王汝言\"<wangry@cqupt.edu.cn>;\"王毅\"<wangyi81@cqupt.edu.cn>;\"王正强\"<wangzq@cqupt.edu.cn>;\"王永\"<wangyong@cqupt.edu.cn>;\"蔚承英\"<weicy@cqupt.edu.cn>;\"韦世红\"<weish@cqupt.edu.cn>;\"吴大鹏\"<wudp@cqupt.edu.cn>;\"王平\"<wp@cqupt.edu.cn>;\"武俊\"<wujun@cqupt.edu.cn>;\"吴坤君\"<wukj@cqupt.edu.cn>;\"向劲松\"<xiangjs@cqupt.edu.cn>;\"余翔\"<xiangyu@cqupt.edu.cn>;\"鲜永菊\"<xianyj@cqupt.edu.cn>;\"夏绪玖\"<xiaxj@cqupt.edu.cn>;\"熊炼\"<xionglian@cqupt.edu.cn>;\"谢良波\"<xielb@cqupt.edu.cn>;\"徐川\"<xuchuan@cqupt.edu.cn>;\"熊炫睿\"<xiongxr@cqupt.edu.cn>;\"徐鹏\"<xupeng@cqupt.edu.cn>;\"席镯轩\"<xizhen@cqupt.edu.cn>;\"徐文云\"<xuwy@cqupt.edu.cn>;\"徐勇军\"<xuyj@cqupt.edu.cn>;\"杨柳\"<yangliu@cqupt.edu.cn>;\"阳莉\"<yanglit@cqupt.edu.cn>;\"杨路\"<yanglu@cqupt.edu.cn>;\"阎英\"<yanying@cqupt.edu.cn>;\"杨茂斌\"<yangmb@cqupt.edu.cn>;\"杨晓波\"<yangxb@cqupt.edu.cn>;\"杨晓非\"<yangxf@cqupt.edu.cn>;\"杨英\"<yangying@cqupt.edu.cn>;\"叶志红\"<yezh@cqupt.edu.cn>;\"易琛\"<yichen@cqupt.edu.cn>;\"姚玉坤\"<yaoyk@cqupt.edu.cn>;\"殷茜\"<yinqian@cqupt.edu.cn>;\"张毅\"<yizhang@cqupt.edu.cn>;\"袁泉\"<yuanquan@cqupt.edu.cn>;\"于秀兰\"<yuxl@cqupt.edu.cn>;\"余跃\"<yuyue@cqupt.edu.cn>;\"张家波\"<zhangjb@cqupt.edu.cn>;\"张刚\"<zhanggang@cqupt.edu.cn>;\"张海波\"<zhanghb@cqupt.edu.cn>;\"曾帅\"<zengshuai@cqupt.edu.cn>;\"张勤\"<zhangqin@cqupt.edu.cn>;\"张盛峰\"<zhangsf@cqupt.edu.cn>;\"张天骐\"<zhangtq@cqupt.edu.cn>;\"张挺\"<zhangting@cqupt.edu.cn>;\"张祖凡\"<zhangzf@cqupt.edu.cn>;\"郑丹玲\"<zhengdl@cqupt.edu.cn>;\"赵辉\"<zhaohui@cqupt.edu.cn>;\"周非\"<zhoufei@cqupt.edu.cn>;\"周牧\"<zhoumu@cqupt.edu.cn>;\"周翊\"<zhouy@cqupt.edu.cn>;\"周晓霞\"<zhouxx@cqupt.edu.cn>;\"周杨\"<zhouyang@cqupt.edu.cn>;\"庄陵\"<zhuangling@cqupt.edu.cn>;\"朱江\"<zhujiang@cqupt.edu.cn>;\"李长萍\"<licp@cqupt.edu.cn>;\"郑涵英\"<zhenghy@cqupt.edu.cn>;\"杨小龙\"<yangxl@cqupt.edu.cn>;\"廖莎莎\"<liaoss@cqupt.edu.cn>;\"崔亚平\"<cuiyp@cqupt.edu.cn>;\"张普宁\"<zhangpn@cqupt.edu.cn>;\"赵悦\"<zhaoyue@cqupt.edu.cn>;";
//        String[] nameAndEmails = a.split(";");
//        for(String nameAndEmail : nameAndEmails){
//            String name = nameAndEmail.split("<")[0];
//            String email = nameAndEmail.split("<")[1];
//            email = email.split(">")[0];
//            name = name.substring(1);
//            name = name.split("\"")[0];
//            System.out.println(name);
//            System.out.println(email);
//        }
    }

}
