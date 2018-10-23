package com.imooc.smartbulter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.imooc.smartbulter.R;
import com.imooc.smartbulter.adapter.ChatListAdapter;
import com.imooc.smartbulter.entity.ChatListData;
import com.imooc.smartbulter.utils.ShareUtils;
import com.imooc.smartbulter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 * 管家服务
 */

public class BulterFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    //  private Button btn_left,btn_right;

    private List<ChatListData> mlist = new ArrayList<>();
    private ChatListAdapter adapter;

    //tts
    private SpeechSynthesizer mTts;

    //一下为新的

    //输入框
    private EditText et_text;
    //发送
    private Button btn_send;

    //以上
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {

        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");


        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
        // btn_left=(Button)view.findViewById(R.id.btn_left);
        // btn_left.setOnClickListener(this);
        //btn_right=(Button)view.findViewById(R.id.btn_right);
        //btn_right.setOnClickListener(this);

        //一下
        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        //以上
        //设置适配器C
        adapter = new ChatListAdapter(getActivity(), mlist);
        mChatListView.setAdapter(adapter);

        addLeftItem("你好，我是小管家");

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //  case R.id.btn_left:
            //     addLeftItem("左边");
            //      break;
            //  case R.id.btn_right:
            //      addRightItem("右边");
            //      break;

            //一下
            case R.id.btn_send:
                /*
                * 1.获取输入框的内容
                * 2.判断是否为空
                * 3.判断长度不超过30
                * 4.清空当前输入框
                * 5.添加输入的内容到rightitem
                * 6.发送给机器人请求返回
                * 7.机器人的返回值添加在left item
                * */

                //1.拿到数据
                String text = et_text.getText().toString();
                //2.判断是否为空
                if (!TextUtils.isEmpty(text)) {
                    //3.长度不能大于30
                    if (text.length() > 30) {
                        Toast.makeText(getActivity(), "输入长度超出限度", Toast.LENGTH_SHORT).show();

                    } else {
                        //4.清空当前输入框
                       et_text.setText("");
                        //5.添加输入的内容到 rightitem
                        addRightItem(text);

                        //新加的
                        if (text.equals("烫伤怎么办")){
                            addLeftItem("生活中发生烫伤，可以采取以下几种措施： l．对只有轻微红肿的轻度烫伤，可以用冷水反复冲洗，再涂些清凉油就行了。 2．烫伤部位已经起小水泡的，不要弄破它，可以在水泡周围涂擦酒精，用干净的纱布包扎。 3．烫伤比较严重的，应当及时送医院进行诊治。 4．烫伤面积较大的，应尽快脱去衣裤、鞋袜，但不能强行撕脱，必要时应将衣物剪开；烫伤后，要特别注意烫伤部位的清洁，不能随意涂擦外用药品或代用品，防止受到感染，给医院的治疗增加困难。");
                        }if (text.equals("退烧")){
                            addLeftItem("生活中想快速退烧，可采取以下措施:1.如果高烧无法耐受，可以采用冷敷帮助降低体温。在额头、手腕、小腿上各放一块湿冷毛巾，其他部位应以衣物盖住。当冷敷布达到体温时，应换一次，反复直到烧退为止。也可将冰块包在布袋里，放在额头上." +
                                    "2.姜水泡脚：煲姜水泡脚，边加水边泡，一直到出汗为止，估计要30分钟以上才能出汗。这是消除感冒引起的发烧绝佳方法。\n"+"3.酒精擦浴：这是最有效的方法，酒精擦浴一定要用稀释后的酒精！在全身擦。");
                        }if (text.equals("洗羽绒服")){
                            addLeftItem("先将羽绒服放在冷水中浸泡20分钟。用两汤匙左右的洗衣粉倒入水温为20~30度的水中搅匀，然后放入从清水中"+
                                    "捞出并汲取水分的羽绒服，浸泡5~10分钟。将羽绒服从洗涤液中取出，平铺在干净台板上，用软毛刷蘸洗涤液从内到外轻轻刷洗");
                        }if(text.equals("识别新旧大米")){
                            addLeftItem("看：新米色泽呈透明玉色状，未熟粒米可见青色。摸：新米光滑，手摸有凉爽感；陈米色暗，手摸有涩感"+
                                    "闻：新米有股浓浓的清香，陈米少清香味，而存放一年以上的米，只有米糠味，没有清香味。");
                        }if (text.equals("蘑菇中毒")){
                            addLeftItem("蘑菇中毒急救：1.轻症者立即催吐。2.用浓茶水或用1：2000~1：1500高锰酸钾洗胃，洗胃后灌入药用炭10~20克。3.有条件时静脉滴入5%葡萄糖酸水或服用甘草绿豆汤排毒。4.送院抢治");
                        }if (text.equals("口腔溃疡")){
                                addLeftItem("洗漱后，用风油精涂抹于患处，每日2~3次，一两天即可痊愈。");
                        }if (text.equals("除脚臭")){
                            addLeftItem("每晚用热水洗脚时，盆内放入50克明矾，对消除脚臭有效；将土霉素研成细末，抹在脚趾缝里"+
                                    "每次用量1~2片，可使半个月内不在有臭味。");
                        }if (text.equals("中暑急救")){
                                addLeftItem("中暑急救可采取以下措施：1.立即及将病人转移到阴凉通风处，松开其腰带，解开其颈部衣领或脱去外面衣服。2.给患者服用冷盐开水、绿豆汤等。3.口服十滴水、仁丹等防治中暑药品。"+
                                        "4.高热者迅速冷水喷淋，在头部，心前区放冷毛巾降温。");
                        }if (text.equals("除鱼腥")){
                            addLeftItem("用半两盐加如一盆5升左右的水中，把活鱼泡在盐水里一个小时，盐水通过两腮浸入血液，腥味就会消失。即便是死鱼，在盐水里浸泡两小时也可去掉腥味.");
                        }if (text.equals("除甲醛")){
                            addLeftItem("1.300克红茶泡两脸盆水，放在居室内，并开创透气，48小时后室内甲醛含量将下降90%以上，刺激性气味基本消除"+
                                    "2.购买800克活性炭可清除甲醛。将活性炭分成8份，放入盘碟中，每屋子放2—3碟，72小时基本可以除尽室内异味。");
                        }if (text.equals("防冻疮")){
                                addLeftItem("1.食醋疗法：取适量食醋在火上加热，然后取消毒纱布一块蘸醋外敷患处。2.独头蒜法：取独头蒜一个，捣烂后放在太阳下晒热，在易发冻疮出轻轻摩擦"+
                                        "至局部出现一个小泡，然后用消毒针将水泡挑破。");
                        }if (text.equals("电梯下坠")){
                            addLeftItem("电梯下坠自救：1.赶快将每一层的按键全部按下，这样当紧急电源地洞时，电梯可以马上通知继续下坠。2.整个背部跟头部紧贴电梯内墙，呈一直线，利用电梯墙壁保护脊椎。"+
                                    "3.膝盖呈弯曲姿势，因为韧带时人体唯一富含弹性的组织，所以利用膝盖弯曲来承受重击压力。");
                        }if (text.equals("晕车")){
                            addLeftItem("1.口中服药：胃安片1片，晕车严重时可服用2片，于上车前10~15分钟服用，可防晕车。2.头部涂药：乘车途中将风油精搽于太阳穴或风池穴。3.脐部服药：取生姜1片，贴在肚脐上，外用胶布或伤湿止痛膏固定。");
                        }if (text.equals("识别假奶粉")){
                            addLeftItem("1.手捏鉴别：手捏奶粉袋来回摩擦，真奶粉细腻，发出“吱吱”声。假奶粉因掺有葡萄糖、白糖等较粗颗粒，会发出“沙沙”声。2.色泽鉴别：真奶粉呈天然乳黄色，假奶粉颜色较白。"+
                                    "3.溶解速度鉴别：真奶粉用热水冲时有悬浮物上浮的现象，搅拌时黏住调羹。假奶粉用热水冲时，迅速溶解，没有天然乳汁的香味颜色。");
                        }else {
                            String url = "http://op.juhe.cn/robot/index?info=" + text
                                    + "&key=" + StaticClass.CHAT_LIST_KEY;

                            RxVolley.get(url, new HttpCallback() {
                                @Override
                                public void onSuccess(String t) {
                                    // Toast.makeText(getActivity(),"json:"+t,Toast.LENGTH_SHORT).show();
                                    parsingJson(t);
                                }
                            });

                        }
                        //以上
                            //6.发送给机器人请求
                           /* String url = "http://op.juhe.cn/robot/index?info=" + text
                                    + "&key=" + StaticClass.CHAT_LIST_KEY;

                            RxVolley.get(url, new HttpCallback() {
                                @Override
                                public void onSuccess(String t) {
                                    // Toast.makeText(getActivity(),"json:"+t,Toast.LENGTH_SHORT).show();
                                    parsingJson(t);
                                }
                            });*/

                    }//先把这个else隐藏

                } else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;


            //以上
        }

    }

    //解析json
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonresult = jsonObject.getJSONObject("result");
            //拿到返回值
            String text = jsonresult.getString("text");
            //7.拿到机器人的返回值添加在left item
           addLeftItem(text);
            //新加的
            //以上新加的
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //添加左边文本
    private void addLeftItem(String text) {
        //获取状态选择钮状态
        boolean isSpeak= ShareUtils.getBoolean(getActivity(),"isSpeak",false);
        if(isSpeak){
            //调用说话方法
            startSpeak(text);
        }
        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mlist.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());

    }

    //添加右边文本
    private void addRightItem(String text) {
        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        date.setText(text);
        mlist.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());


    }
    //开始说话
    public void startSpeak(String text) {
        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
    }

    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}