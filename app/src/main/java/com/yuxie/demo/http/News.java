package com.yuxie.demo.http;

import java.util.List;

/**
 * Created by Administrator on 2017/09/11.
 */

public class News {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"uniquekey":"cae99872cee6c8b8fca3651afb09b48f","title":"【经典回顾】SKS 半自动步枪","date":"2017-09-11 23:29","category":"头条","author_name":"火器酷","url":"http://mini.eastday.com/mobile/170911232948152.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_88_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_75_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_82_mwpm_03200403.jpg"},{"uniquekey":"b6a76651081e4fd5caf5d68918cd238c","title":"我们是谁，新训骨干","date":"2017-09-11 21:53","category":"头条","author_name":"消费日报网","url":"http://mini.eastday.com/mobile/170911215348712.html","thumbnail_pic_s":"http://09.imgmini.eastday.com/mobile/20170911/20170911215348_9c2dc74f09bd5531ed26d74bc0b5446f_5_mwpm_03200403.jpg","thumbnail_pic_s02":"http://09.imgmini.eastday.com/mobile/20170911/20170911215348_9c2dc74f09bd5531ed26d74bc0b5446f_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://09.imgmini.eastday.com/mobile/20170911/20170911215348_9c2dc74f09bd5531ed26d74bc0b5446f_3_mwpm_03200403.jpg"},{"uniquekey":"6b59e76c39968bb06ca0fe0e5665ec9f","title":"善心汇主犯张天明等人被逮捕 非法获利22亿余元","date":"2017-09-11 18:36","category":"头条","author_name":"人民日报","url":"http://mini.eastday.com/mobile/170911183606929.html","thumbnail_pic_s":"http://07.imgmini.eastday.com/mobile/20170911/20170911183606_67dc80ba56538c3df2f9adc88a4f55ee_1_mwpm_03200403.JPEG"},{"uniquekey":"320cbc297665b140e38c882dfcab4132","title":"京沈客专沈阳枢纽最大规模封锁转线施工完成","date":"2017-09-11 18:30","category":"头条","author_name":"人民铁道报","url":"http://mini.eastday.com/mobile/170911183019827.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911183019_a48ad6c0bbb14ab3b8f67adf2589ca4c_1_mwpm_03200403.jpg"},{"uniquekey":"092f22c6a4fe8afaa524349bd2c06d9d","title":"英国警方出新招应对汽车撞人恐袭：可1分钟内部署反撞刺钉网","date":"2017-09-11 18:25","category":"头条","author_name":"澎湃新闻网","url":"http://mini.eastday.com/mobile/170911182524352.html","thumbnail_pic_s":"http://00.imgmini.eastday.com/mobile/20170911/20170911182524_304d6193cb375ac4002f737ae95a09af_1_mwpm_03200403.jpg"},{"uniquekey":"8ffb43baa60d985b181409ff066e3bb2","title":"为什么晚上睡觉经常做梦，原来是这个原因！","date":"2017-09-11 18:11","category":"头条","author_name":"中华养生网","url":"http://mini.eastday.com/mobile/170911181115702.html","thumbnail_pic_s":"http://05.imgmini.eastday.com/mobile/20170911/20170911181115_cadff6f9f32d3dfa845ad9a421bbb747_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://05.imgmini.eastday.com/mobile/20170911/20170911181115_cadff6f9f32d3dfa845ad9a421bbb747_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://05.imgmini.eastday.com/mobile/20170911/20170911181115_cadff6f9f32d3dfa845ad9a421bbb747_3_mwpm_03200403.jpg"},{"uniquekey":"08628dfe8a457e8f5ff5a06d7a96694c","title":"飓风蹂躏后，佛罗里达成末日景象，加勒比快被夷为平地","date":"2017-09-11 18:04","category":"头条","author_name":"未来网","url":"http://mini.eastday.com/mobile/170911180454481.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911180454_567965dccbbe805bbd7bbc6169a46f5f_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911180454_567965dccbbe805bbd7bbc6169a46f5f_6_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911180454_567965dccbbe805bbd7bbc6169a46f5f_8_mwpm_03200403.jpg"},{"uniquekey":"15b0c687492d099efbcd4a27517847b2","title":"防晒产品不合格？ 上海新高姿：与我们没有任何关系","date":"2017-09-11 18:00","category":"头条","author_name":"中国网财经","url":"http://mini.eastday.com/mobile/170911180050959.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911180050_d1082aade1efffde4c18c449624aa5aa_1_mwpm_03200403.jpg"},{"uniquekey":"e2810812532904e6747138c6167391e9","title":"遇到这几个生肖就娶了吧，最会省钱持家的生肖","date":"2017-09-11 17:51","category":"头条","author_name":"搜狐","url":"http://mini.eastday.com/mobile/170911175104591.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911175104_165ecae6fa357040bbf154946923793b_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911175104_165ecae6fa357040bbf154946923793b_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911175104_165ecae6fa357040bbf154946923793b_1_mwpm_03200403.jpg"},{"uniquekey":"04979c0127fd3acfa0d1504398e1f767","title":"共享单车在新加坡屡屡遭虐 小米共享小白车还敢来","date":"2017-09-11 17:50","category":"头条","author_name":"新加坡万事通","url":"http://mini.eastday.com/mobile/170911175043693.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911175043_d41d8cd98f00b204e9800998ecf8427e_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911175043_d41d8cd98f00b204e9800998ecf8427e_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911175043_d41d8cd98f00b204e9800998ecf8427e_4_mwpm_03200403.jpg"},{"uniquekey":"8672678cf4484c9536bfae5b9814353f","title":"男子野外钓鱼，看见一条罗非鱼所做的一幕，不敢相信自己眼睛","date":"2017-09-11 17:40","category":"头条","author_name":"历史大课堂","url":"http://mini.eastday.com/mobile/170911174031840.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911_515b01759f749e99d2f1e0e8cebd7723_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911_042c5e3c738ec47beca6bdf8b0fe8185_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911_54bdf4978312cc76fcadde7943cd9815_mwpm_03200403.jpg"},{"uniquekey":"1215756c4b9b046a27d7fb7a9ae9396e","title":"天津港集团原董事长张丽丽被\"双开\" 涉跑官要官等(图|简历)","date":"2017-09-11 17:38","category":"头条","author_name":"经济日报-中国经济网","url":"http://mini.eastday.com/mobile/170911173826425.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911173826_b9efdf8f14976270b0a7ac739a80fa52_1_mwpm_03200403.jpg"},{"uniquekey":"d68473f9779458f7eafb62ff963bf5cc","title":"这次特朗普只能撤退！否则不用普京出手，美军直接损失百架战机","date":"2017-09-11 17:33","category":"头条","author_name":"123军情观察室","url":"http://mini.eastday.com/mobile/170911173309285.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911_a2743156ec1f47e87834fa9d618338df_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170911/20170911_915843f17417a75feae08f833e4fdee5_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170911/20170911_c808bcd92a0d95bb04886b539c98e2af_cover_mwpm_03200403.jpg"},{"uniquekey":"fa16a1dbf14409631a43a38a593399c1","title":"9·11灾后罕见照片公布 五角大楼内时钟定格灾难时刻","date":"2017-09-11 17:31","category":"头条","author_name":"国际在线","url":"http://mini.eastday.com/mobile/170911173114369.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911173114_7864832e81ecba8b30dcfa6e7070656c_5_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170911/20170911173114_eabd582e5f4f1e59a9d8aa188b7f0e92_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170911/20170911173114_3852f07d57ebbdce50973e06c586acca_6_mwpm_03200403.jpg"},{"uniquekey":"29272bf04b58379d711c448a52d38285","title":"北京一快递员送快递被打致高位截瘫 肇事者被拘","date":"2017-09-11 17:29","category":"头条","author_name":"法制晚报","url":"http://mini.eastday.com/mobile/170911172901487.html","thumbnail_pic_s":"http://07.imgmini.eastday.com/mobile/20170911/20170911172901_d41d8cd98f00b204e9800998ecf8427e_1_mwpm_03200403.jpg"},{"uniquekey":"cd3e747318de3fbe29f48d24ef2cfada","title":"人民币天天涨，央妈又出手了，换汇窗口开启？","date":"2017-09-11 17:24","category":"头条","author_name":"海外财富网","url":"http://mini.eastday.com/mobile/170911172456740.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911_9cc52e490beea5c6682736634e7b6552_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20170911/20170911_1badb3e3dbe1dbb9d0565863482ecf4d_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20170911/20170911_63066ef4ec6bf50c5a905e70136764f5_cover_mwpm_03200403.jpg"},{"uniquekey":"923eab5dee51763c9acc11d963d8c1c1","title":"世界华文传媒论坛福州宣言（全文）","date":"2017-09-11 17:12","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170911171236736.html","thumbnail_pic_s":"http://01.imgmini.eastday.com/mobile/20170911/20170911171236_00612202cd2615c697f118f9f10a33ea_1_mwpm_03200403.jpg"},{"uniquekey":"bacb7edb0b43ca584d118c9ff43352a1","title":"美国真的钟爱\u201c航行自由\u201d吗？它曾是\u201c海盗\u201d的幕后老板","date":"2017-09-11 17:11","category":"头条","author_name":"澎湃新闻网","url":"http://mini.eastday.com/mobile/170911171111621.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911171111_37d92ff77444d935e339e33750a2129f_1_mwpm_03200403.jpg"},{"uniquekey":"2d2a6268d39680ad000aa9f597455187","title":"无人便利店亮相海口白沙门公园","date":"2017-09-11 17:02","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170911170231030.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911170231_33e428273d7542b95d3ac280b07e65d8_3_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06.imgmini.eastday.com/mobile/20170911/20170911170231_33e428273d7542b95d3ac280b07e65d8_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06.imgmini.eastday.com/mobile/20170911/20170911170231_33e428273d7542b95d3ac280b07e65d8_2_mwpm_03200403.jpg"},{"uniquekey":"def7fab2fa9c9c228f7b405c265a5fdc","title":"深圳福田发生枪击伤人事件","date":"2017-09-11 16:55","category":"头条","author_name":"福田警察","url":"http://mini.eastday.com/mobile/170911165533865.html","thumbnail_pic_s":"http://04.imgmini.eastday.com/mobile/20170911/20170911165533_d41d8cd98f00b204e9800998ecf8427e_1_mwpm_03200403.jpg"},{"uniquekey":"092224f5bfc91dca55941d1545dac39d","title":"高速上爆胎了，自己下车换胎算违章吗？","date":"2017-09-11 16:52","category":"头条","author_name":"驾照吧","url":"http://mini.eastday.com/mobile/170911165201505.html","thumbnail_pic_s":"http://00.imgmini.eastday.com/mobile/20170911/20170911165201_a7366a82ccb4b709caae352fb5aae351_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://00.imgmini.eastday.com/mobile/20170911/20170911165201_a7366a82ccb4b709caae352fb5aae351_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://00.imgmini.eastday.com/mobile/20170911/20170911165201_a7366a82ccb4b709caae352fb5aae351_3_mwpm_03200403.jpg"},{"uniquekey":"b764bb6a4fd0f9573a355d92e5422a4f","title":"月工资2000，月供1500房贷，三四线城市的伤","date":"2017-09-11 16:47","category":"头条","author_name":"网贷之家","url":"http://mini.eastday.com/mobile/170911164734522.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911164734_93b03acfcbb28607c25832c60dba03e7_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911164734_93b03acfcbb28607c25832c60dba03e7_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911164734_93b03acfcbb28607c25832c60dba03e7_4_mwpm_03200403.jpg"},{"uniquekey":"cea3a37378a2c78603571f55dbff274c","title":"辽宁：加大警示教育力度是避免重蹈覆辙的净化和预防之举","date":"2017-09-11 16:45","category":"头条","author_name":"中国纪检监察报","url":"http://mini.eastday.com/mobile/170911164504740.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911164504_42f8d54f7bd80a4b2753da6ea5b845ed_1_mwpm_03200403.jpg"},{"uniquekey":"18c8284b23c3b86bd3ece35a0200fc4b","title":"发达国家日本：穷人真实生活现状","date":"2017-09-11 16:42","category":"头条","author_name":"说不得大师","url":"http://mini.eastday.com/mobile/170911164250055.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911_5c1cc4bba459de1ba19c75e0a0e8a416_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20170911/20170911_6872a83953af11d2d75afda378a620f8_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20170911/20170911_e576312f7fcf7e689925da04f30b2c00_cover_mwpm_03200403.jpg"},{"uniquekey":"f4e827a33f130770664023995024ce34","title":"新疆：天山麦海开镰秋收","date":"2017-09-11 16:31","category":"头条","author_name":"新华社","url":"http://mini.eastday.com/mobile/170911163149533.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911163149_253a9da3fe32d34e2c7a2f30045e5541_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06.imgmini.eastday.com/mobile/20170911/20170911163149_2b25001c48dab2329dc34f5addb6bb8f_7_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06.imgmini.eastday.com/mobile/20170911/20170911163149_7755e99c8c29b8b31b49b096ea999b98_3_mwpm_03200403.jpg"},{"uniquekey":"c619efc7b9c556437d6d7de59410f5cb","title":"罗文赴广东省调研制造业创新中心","date":"2017-09-11 16:31","category":"头条","author_name":"工业和信息化部","url":"http://mini.eastday.com/mobile/170911163134470.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911163134_0e037386dae3593f0b6e860bc653c338_1_mwpm_03200403.jpg"},{"uniquekey":"68b0d3d1b5a8e9bcef3e619aceb2de8d","title":"洋葱千万不能这样吃了, 不但不抗癌, 还会变得致癌! 别再犯傻了!","date":"2017-09-11 16:20","category":"头条","author_name":"红城山水","url":"http://mini.eastday.com/mobile/170911162049909.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911162049_d41d8cd98f00b204e9800998ecf8427e_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911162049_d41d8cd98f00b204e9800998ecf8427e_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911162049_d41d8cd98f00b204e9800998ecf8427e_1_mwpm_03200403.jpg"},{"uniquekey":"310dda5deb4bfec82308ddbaac7df567","title":"宋庆龄临终前日思夜想的人是谁？一句\u201c我想她\u201d泪下","date":"2017-09-11 16:17","category":"头条","author_name":"风云时讯","url":"http://mini.eastday.com/mobile/170911161741213.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911161741_899a4dfad8595b3d72689a2d0cc91bca_3_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170911/20170911161741_899a4dfad8595b3d72689a2d0cc91bca_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170911/20170911161741_899a4dfad8595b3d72689a2d0cc91bca_2_mwpm_03200403.jpg"},{"uniquekey":"1f049d7cb77ff548994d40e18a20a1af","title":"美国人封了16年的911高清视频许多室内镜头第一次公布绝对震撼！","date":"2017-09-11 16:16","category":"头条","author_name":"石明石雕塑艺术","url":"http://mini.eastday.com/mobile/170911161613230.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911161613_294acb5c13e5055e088f2a704d08f97c_10_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911161613_294acb5c13e5055e088f2a704d08f97c_20_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911161613_294acb5c13e5055e088f2a704d08f97c_13_mwpm_03200403.jpg"},{"uniquekey":"c5d9932779f4e93cf016240d613ea1e1","title":"古代青楼的三句行话，现在却成为了流行语","date":"2017-09-11 16:15","category":"头条","author_name":"豆豆酱谈史","url":"http://mini.eastday.com/mobile/170911161530744.html","thumbnail_pic_s":"http://09.imgmini.eastday.com/mobile/20170911/20170911161530_d895f47100233cb3173eecca1f91fcf9_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://09.imgmini.eastday.com/mobile/20170911/20170911161530_d895f47100233cb3173eecca1f91fcf9_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://09.imgmini.eastday.com/mobile/20170911/20170911161530_d895f47100233cb3173eecca1f91fcf9_1_mwpm_03200403.jpg"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * stat : 1
         * data : [{"uniquekey":"cae99872cee6c8b8fca3651afb09b48f","title":"【经典回顾】SKS 半自动步枪","date":"2017-09-11 23:29","category":"头条","author_name":"火器酷","url":"http://mini.eastday.com/mobile/170911232948152.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_88_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_75_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_82_mwpm_03200403.jpg"},{"uniquekey":"b6a76651081e4fd5caf5d68918cd238c","title":"我们是谁，新训骨干","date":"2017-09-11 21:53","category":"头条","author_name":"消费日报网","url":"http://mini.eastday.com/mobile/170911215348712.html","thumbnail_pic_s":"http://09.imgmini.eastday.com/mobile/20170911/20170911215348_9c2dc74f09bd5531ed26d74bc0b5446f_5_mwpm_03200403.jpg","thumbnail_pic_s02":"http://09.imgmini.eastday.com/mobile/20170911/20170911215348_9c2dc74f09bd5531ed26d74bc0b5446f_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://09.imgmini.eastday.com/mobile/20170911/20170911215348_9c2dc74f09bd5531ed26d74bc0b5446f_3_mwpm_03200403.jpg"},{"uniquekey":"6b59e76c39968bb06ca0fe0e5665ec9f","title":"善心汇主犯张天明等人被逮捕 非法获利22亿余元","date":"2017-09-11 18:36","category":"头条","author_name":"人民日报","url":"http://mini.eastday.com/mobile/170911183606929.html","thumbnail_pic_s":"http://07.imgmini.eastday.com/mobile/20170911/20170911183606_67dc80ba56538c3df2f9adc88a4f55ee_1_mwpm_03200403.JPEG"},{"uniquekey":"320cbc297665b140e38c882dfcab4132","title":"京沈客专沈阳枢纽最大规模封锁转线施工完成","date":"2017-09-11 18:30","category":"头条","author_name":"人民铁道报","url":"http://mini.eastday.com/mobile/170911183019827.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911183019_a48ad6c0bbb14ab3b8f67adf2589ca4c_1_mwpm_03200403.jpg"},{"uniquekey":"092f22c6a4fe8afaa524349bd2c06d9d","title":"英国警方出新招应对汽车撞人恐袭：可1分钟内部署反撞刺钉网","date":"2017-09-11 18:25","category":"头条","author_name":"澎湃新闻网","url":"http://mini.eastday.com/mobile/170911182524352.html","thumbnail_pic_s":"http://00.imgmini.eastday.com/mobile/20170911/20170911182524_304d6193cb375ac4002f737ae95a09af_1_mwpm_03200403.jpg"},{"uniquekey":"8ffb43baa60d985b181409ff066e3bb2","title":"为什么晚上睡觉经常做梦，原来是这个原因！","date":"2017-09-11 18:11","category":"头条","author_name":"中华养生网","url":"http://mini.eastday.com/mobile/170911181115702.html","thumbnail_pic_s":"http://05.imgmini.eastday.com/mobile/20170911/20170911181115_cadff6f9f32d3dfa845ad9a421bbb747_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://05.imgmini.eastday.com/mobile/20170911/20170911181115_cadff6f9f32d3dfa845ad9a421bbb747_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://05.imgmini.eastday.com/mobile/20170911/20170911181115_cadff6f9f32d3dfa845ad9a421bbb747_3_mwpm_03200403.jpg"},{"uniquekey":"08628dfe8a457e8f5ff5a06d7a96694c","title":"飓风蹂躏后，佛罗里达成末日景象，加勒比快被夷为平地","date":"2017-09-11 18:04","category":"头条","author_name":"未来网","url":"http://mini.eastday.com/mobile/170911180454481.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911180454_567965dccbbe805bbd7bbc6169a46f5f_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911180454_567965dccbbe805bbd7bbc6169a46f5f_6_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911180454_567965dccbbe805bbd7bbc6169a46f5f_8_mwpm_03200403.jpg"},{"uniquekey":"15b0c687492d099efbcd4a27517847b2","title":"防晒产品不合格？ 上海新高姿：与我们没有任何关系","date":"2017-09-11 18:00","category":"头条","author_name":"中国网财经","url":"http://mini.eastday.com/mobile/170911180050959.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911180050_d1082aade1efffde4c18c449624aa5aa_1_mwpm_03200403.jpg"},{"uniquekey":"e2810812532904e6747138c6167391e9","title":"遇到这几个生肖就娶了吧，最会省钱持家的生肖","date":"2017-09-11 17:51","category":"头条","author_name":"搜狐","url":"http://mini.eastday.com/mobile/170911175104591.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911175104_165ecae6fa357040bbf154946923793b_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911175104_165ecae6fa357040bbf154946923793b_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911175104_165ecae6fa357040bbf154946923793b_1_mwpm_03200403.jpg"},{"uniquekey":"04979c0127fd3acfa0d1504398e1f767","title":"共享单车在新加坡屡屡遭虐 小米共享小白车还敢来","date":"2017-09-11 17:50","category":"头条","author_name":"新加坡万事通","url":"http://mini.eastday.com/mobile/170911175043693.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911175043_d41d8cd98f00b204e9800998ecf8427e_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911175043_d41d8cd98f00b204e9800998ecf8427e_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911175043_d41d8cd98f00b204e9800998ecf8427e_4_mwpm_03200403.jpg"},{"uniquekey":"8672678cf4484c9536bfae5b9814353f","title":"男子野外钓鱼，看见一条罗非鱼所做的一幕，不敢相信自己眼睛","date":"2017-09-11 17:40","category":"头条","author_name":"历史大课堂","url":"http://mini.eastday.com/mobile/170911174031840.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911_515b01759f749e99d2f1e0e8cebd7723_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911_042c5e3c738ec47beca6bdf8b0fe8185_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911_54bdf4978312cc76fcadde7943cd9815_mwpm_03200403.jpg"},{"uniquekey":"1215756c4b9b046a27d7fb7a9ae9396e","title":"天津港集团原董事长张丽丽被\"双开\" 涉跑官要官等(图|简历)","date":"2017-09-11 17:38","category":"头条","author_name":"经济日报-中国经济网","url":"http://mini.eastday.com/mobile/170911173826425.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911173826_b9efdf8f14976270b0a7ac739a80fa52_1_mwpm_03200403.jpg"},{"uniquekey":"d68473f9779458f7eafb62ff963bf5cc","title":"这次特朗普只能撤退！否则不用普京出手，美军直接损失百架战机","date":"2017-09-11 17:33","category":"头条","author_name":"123军情观察室","url":"http://mini.eastday.com/mobile/170911173309285.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911_a2743156ec1f47e87834fa9d618338df_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170911/20170911_915843f17417a75feae08f833e4fdee5_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170911/20170911_c808bcd92a0d95bb04886b539c98e2af_cover_mwpm_03200403.jpg"},{"uniquekey":"fa16a1dbf14409631a43a38a593399c1","title":"9·11灾后罕见照片公布 五角大楼内时钟定格灾难时刻","date":"2017-09-11 17:31","category":"头条","author_name":"国际在线","url":"http://mini.eastday.com/mobile/170911173114369.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911173114_7864832e81ecba8b30dcfa6e7070656c_5_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170911/20170911173114_eabd582e5f4f1e59a9d8aa188b7f0e92_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170911/20170911173114_3852f07d57ebbdce50973e06c586acca_6_mwpm_03200403.jpg"},{"uniquekey":"29272bf04b58379d711c448a52d38285","title":"北京一快递员送快递被打致高位截瘫 肇事者被拘","date":"2017-09-11 17:29","category":"头条","author_name":"法制晚报","url":"http://mini.eastday.com/mobile/170911172901487.html","thumbnail_pic_s":"http://07.imgmini.eastday.com/mobile/20170911/20170911172901_d41d8cd98f00b204e9800998ecf8427e_1_mwpm_03200403.jpg"},{"uniquekey":"cd3e747318de3fbe29f48d24ef2cfada","title":"人民币天天涨，央妈又出手了，换汇窗口开启？","date":"2017-09-11 17:24","category":"头条","author_name":"海外财富网","url":"http://mini.eastday.com/mobile/170911172456740.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911_9cc52e490beea5c6682736634e7b6552_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20170911/20170911_1badb3e3dbe1dbb9d0565863482ecf4d_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20170911/20170911_63066ef4ec6bf50c5a905e70136764f5_cover_mwpm_03200403.jpg"},{"uniquekey":"923eab5dee51763c9acc11d963d8c1c1","title":"世界华文传媒论坛福州宣言（全文）","date":"2017-09-11 17:12","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170911171236736.html","thumbnail_pic_s":"http://01.imgmini.eastday.com/mobile/20170911/20170911171236_00612202cd2615c697f118f9f10a33ea_1_mwpm_03200403.jpg"},{"uniquekey":"bacb7edb0b43ca584d118c9ff43352a1","title":"美国真的钟爱\u201c航行自由\u201d吗？它曾是\u201c海盗\u201d的幕后老板","date":"2017-09-11 17:11","category":"头条","author_name":"澎湃新闻网","url":"http://mini.eastday.com/mobile/170911171111621.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911171111_37d92ff77444d935e339e33750a2129f_1_mwpm_03200403.jpg"},{"uniquekey":"2d2a6268d39680ad000aa9f597455187","title":"无人便利店亮相海口白沙门公园","date":"2017-09-11 17:02","category":"头条","author_name":"中国新闻网","url":"http://mini.eastday.com/mobile/170911170231030.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911170231_33e428273d7542b95d3ac280b07e65d8_3_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06.imgmini.eastday.com/mobile/20170911/20170911170231_33e428273d7542b95d3ac280b07e65d8_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06.imgmini.eastday.com/mobile/20170911/20170911170231_33e428273d7542b95d3ac280b07e65d8_2_mwpm_03200403.jpg"},{"uniquekey":"def7fab2fa9c9c228f7b405c265a5fdc","title":"深圳福田发生枪击伤人事件","date":"2017-09-11 16:55","category":"头条","author_name":"福田警察","url":"http://mini.eastday.com/mobile/170911165533865.html","thumbnail_pic_s":"http://04.imgmini.eastday.com/mobile/20170911/20170911165533_d41d8cd98f00b204e9800998ecf8427e_1_mwpm_03200403.jpg"},{"uniquekey":"092224f5bfc91dca55941d1545dac39d","title":"高速上爆胎了，自己下车换胎算违章吗？","date":"2017-09-11 16:52","category":"头条","author_name":"驾照吧","url":"http://mini.eastday.com/mobile/170911165201505.html","thumbnail_pic_s":"http://00.imgmini.eastday.com/mobile/20170911/20170911165201_a7366a82ccb4b709caae352fb5aae351_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://00.imgmini.eastday.com/mobile/20170911/20170911165201_a7366a82ccb4b709caae352fb5aae351_2_mwpm_03200403.jpg","thumbnail_pic_s03":"http://00.imgmini.eastday.com/mobile/20170911/20170911165201_a7366a82ccb4b709caae352fb5aae351_3_mwpm_03200403.jpg"},{"uniquekey":"b764bb6a4fd0f9573a355d92e5422a4f","title":"月工资2000，月供1500房贷，三四线城市的伤","date":"2017-09-11 16:47","category":"头条","author_name":"网贷之家","url":"http://mini.eastday.com/mobile/170911164734522.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911164734_93b03acfcbb28607c25832c60dba03e7_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911164734_93b03acfcbb28607c25832c60dba03e7_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911164734_93b03acfcbb28607c25832c60dba03e7_4_mwpm_03200403.jpg"},{"uniquekey":"cea3a37378a2c78603571f55dbff274c","title":"辽宁：加大警示教育力度是避免重蹈覆辙的净化和预防之举","date":"2017-09-11 16:45","category":"头条","author_name":"中国纪检监察报","url":"http://mini.eastday.com/mobile/170911164504740.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911164504_42f8d54f7bd80a4b2753da6ea5b845ed_1_mwpm_03200403.jpg"},{"uniquekey":"18c8284b23c3b86bd3ece35a0200fc4b","title":"发达国家日本：穷人真实生活现状","date":"2017-09-11 16:42","category":"头条","author_name":"说不得大师","url":"http://mini.eastday.com/mobile/170911164250055.html","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20170911/20170911_5c1cc4bba459de1ba19c75e0a0e8a416_cover_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20170911/20170911_6872a83953af11d2d75afda378a620f8_cover_mwpm_03200403.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20170911/20170911_e576312f7fcf7e689925da04f30b2c00_cover_mwpm_03200403.jpg"},{"uniquekey":"f4e827a33f130770664023995024ce34","title":"新疆：天山麦海开镰秋收","date":"2017-09-11 16:31","category":"头条","author_name":"新华社","url":"http://mini.eastday.com/mobile/170911163149533.html","thumbnail_pic_s":"http://06.imgmini.eastday.com/mobile/20170911/20170911163149_253a9da3fe32d34e2c7a2f30045e5541_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://06.imgmini.eastday.com/mobile/20170911/20170911163149_2b25001c48dab2329dc34f5addb6bb8f_7_mwpm_03200403.jpg","thumbnail_pic_s03":"http://06.imgmini.eastday.com/mobile/20170911/20170911163149_7755e99c8c29b8b31b49b096ea999b98_3_mwpm_03200403.jpg"},{"uniquekey":"c619efc7b9c556437d6d7de59410f5cb","title":"罗文赴广东省调研制造业创新中心","date":"2017-09-11 16:31","category":"头条","author_name":"工业和信息化部","url":"http://mini.eastday.com/mobile/170911163134470.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911163134_0e037386dae3593f0b6e860bc653c338_1_mwpm_03200403.jpg"},{"uniquekey":"68b0d3d1b5a8e9bcef3e619aceb2de8d","title":"洋葱千万不能这样吃了, 不但不抗癌, 还会变得致癌! 别再犯傻了!","date":"2017-09-11 16:20","category":"头条","author_name":"红城山水","url":"http://mini.eastday.com/mobile/170911162049909.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911162049_d41d8cd98f00b204e9800998ecf8427e_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911162049_d41d8cd98f00b204e9800998ecf8427e_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911162049_d41d8cd98f00b204e9800998ecf8427e_1_mwpm_03200403.jpg"},{"uniquekey":"310dda5deb4bfec82308ddbaac7df567","title":"宋庆龄临终前日思夜想的人是谁？一句\u201c我想她\u201d泪下","date":"2017-09-11 16:17","category":"头条","author_name":"风云时讯","url":"http://mini.eastday.com/mobile/170911161741213.html","thumbnail_pic_s":"http://03.imgmini.eastday.com/mobile/20170911/20170911161741_899a4dfad8595b3d72689a2d0cc91bca_3_mwpm_03200403.jpg","thumbnail_pic_s02":"http://03.imgmini.eastday.com/mobile/20170911/20170911161741_899a4dfad8595b3d72689a2d0cc91bca_1_mwpm_03200403.jpg","thumbnail_pic_s03":"http://03.imgmini.eastday.com/mobile/20170911/20170911161741_899a4dfad8595b3d72689a2d0cc91bca_2_mwpm_03200403.jpg"},{"uniquekey":"1f049d7cb77ff548994d40e18a20a1af","title":"美国人封了16年的911高清视频许多室内镜头第一次公布绝对震撼！","date":"2017-09-11 16:16","category":"头条","author_name":"石明石雕塑艺术","url":"http://mini.eastday.com/mobile/170911161613230.html","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20170911/20170911161613_294acb5c13e5055e088f2a704d08f97c_10_mwpm_03200403.jpg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20170911/20170911161613_294acb5c13e5055e088f2a704d08f97c_20_mwpm_03200403.jpg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20170911/20170911161613_294acb5c13e5055e088f2a704d08f97c_13_mwpm_03200403.jpg"},{"uniquekey":"c5d9932779f4e93cf016240d613ea1e1","title":"古代青楼的三句行话，现在却成为了流行语","date":"2017-09-11 16:15","category":"头条","author_name":"豆豆酱谈史","url":"http://mini.eastday.com/mobile/170911161530744.html","thumbnail_pic_s":"http://09.imgmini.eastday.com/mobile/20170911/20170911161530_d895f47100233cb3173eecca1f91fcf9_2_mwpm_03200403.jpg","thumbnail_pic_s02":"http://09.imgmini.eastday.com/mobile/20170911/20170911161530_d895f47100233cb3173eecca1f91fcf9_3_mwpm_03200403.jpg","thumbnail_pic_s03":"http://09.imgmini.eastday.com/mobile/20170911/20170911161530_d895f47100233cb3173eecca1f91fcf9_1_mwpm_03200403.jpg"}]
         */

        private String stat;
        private List<DataBean> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * uniquekey : cae99872cee6c8b8fca3651afb09b48f
             * title : 【经典回顾】SKS 半自动步枪
             * date : 2017-09-11 23:29
             * category : 头条
             * author_name : 火器酷
             * url : http://mini.eastday.com/mobile/170911232948152.html
             * thumbnail_pic_s : http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_88_mwpm_03200403.jpg
             * thumbnail_pic_s02 : http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_75_mwpm_03200403.jpg
             * thumbnail_pic_s03 : http://08.imgmini.eastday.com/mobile/20170911/20170911232948_14cdbfaa1385c07fc45ac28d1d745a51_82_mwpm_03200403.jpg
             */

            private String uniquekey;
            private String title;
            private String date;
            private String category;
            private String author_name;
            private String url;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s02;
            private String thumbnail_pic_s03;

            public String getUniquekey() {
                return uniquekey;
            }

            public void setUniquekey(String uniquekey) {
                this.uniquekey = uniquekey;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }

            public String getThumbnail_pic_s02() {
                return thumbnail_pic_s02;
            }

            public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
                this.thumbnail_pic_s02 = thumbnail_pic_s02;
            }

            public String getThumbnail_pic_s03() {
                return thumbnail_pic_s03;
            }

            public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
            }
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "stat='" + stat + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "News{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
