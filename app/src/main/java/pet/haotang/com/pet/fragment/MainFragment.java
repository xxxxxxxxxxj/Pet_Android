package pet.haotang.com.pet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;
import pet.haotang.com.pet.ADActivity;
import pet.haotang.com.pet.FeedBackActivity;
import pet.haotang.com.pet.MainActivity;
import pet.haotang.com.pet.MainToBeauList;
import pet.haotang.com.pet.MipcaActivityCapture;
import pet.haotang.com.pet.PetCircleInsideActivity;
import pet.haotang.com.pet.R;
import pet.haotang.com.pet.SelectServiceAreaActivity;
import pet.haotang.com.pet.adapter.HotBeauticianAdapter;
import pet.haotang.com.pet.adapter.MainCharacteristicServiceAdapter;
import pet.haotang.com.pet.adapter.MainHospitalAdapter;
import pet.haotang.com.pet.adapter.MainPetCircleAdapter;
import pet.haotang.com.pet.adapter.MainPetEncyclopediaAdapter;
import pet.haotang.com.pet.adapter.MainServiceAdapter;
import pet.haotang.com.pet.base.BaseFragment;
import pet.haotang.com.pet.entity.BannerBean;
import pet.haotang.com.pet.entity.Beautician;
import pet.haotang.com.pet.entity.HomeTopMsg;
import pet.haotang.com.pet.entity.MainCharacteristicService;
import pet.haotang.com.pet.entity.MainHospital;
import pet.haotang.com.pet.entity.MainPetCircle;
import pet.haotang.com.pet.entity.MainPetEncyclopedia;
import pet.haotang.com.pet.entity.MainService;
import pet.haotang.com.pet.entity.PetCircle;
import pet.haotang.com.pet.util.CacheKeyUtils;
import pet.haotang.com.pet.util.GlideUtils;
import pet.haotang.com.pet.util.Global;
import pet.haotang.com.pet.util.LogUtils;
import pet.haotang.com.pet.util.Market;
import pet.haotang.com.pet.util.OkGoUtils;
import pet.haotang.com.pet.util.ToastUtil;
import pet.haotang.com.pet.util.UmengStatistics;
import pet.haotang.com.pet.util.UrlContans;
import pet.haotang.com.pet.util.Utils;
import pet.haotang.com.pet.view.MarqueeView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/21 21:06
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.iv_mainfragment_scan)
    ImageView ivMainfragmentScan;
    @BindView(R.id.iv_mainfragment_tousu)
    ImageView ivMainfragmentTousu;
    @BindView(R.id.tv_mainfragment_areaname)
    TextView tvMainfragmentAreaname;
    @BindView(R.id.rl_mainfragment_title)
    RelativeLayout rlMainfragmentTitle;
    @BindView(R.id.iv_mainfragment_jyzb)
    ImageView ivMainfragmentJyzb;
    @BindView(R.id.rl_mainfragment_jyzb_delete)
    RelativeLayout rlMainfragmentJyzbDelete;
    @BindView(R.id.tv_mainfragment_jyzb_name)
    TextView tvMainfragmentJyzbName;
    @BindView(R.id.rl_mainfragment_jyzb)
    RelativeLayout rlMainfragmentJyzb;
    @BindView(R.id.tv_mainfragmentcontent_topmsg_msg)
    MarqueeView tvMainfragmentcontentTopmsgMsg;
    @BindView(R.id.iv_mainfragmentcontent_newred)
    ImageView ivMainfragmentcontentNewred;
    @BindView(R.id.rl_mainfragmentcontent_topmsg)
    RelativeLayout rlMainfragmentcontentTopmsg;
    @BindView(R.id.rvpBanner5)
    Banner rvpBanner5;
    @BindView(R.id.gv_mainfragmentcontent_service)
    RecyclerView gvMainfragmentcontentService;
    @BindView(R.id.rvp_mainfragment_banner1)
    Banner rvpMainfragmentBanner1;
    @BindView(R.id.tv_mainfragmentcontent_hotbeautician)
    TextView tvMainfragmentcontentHotbeautician;
    @BindView(R.id.iv_mainfragmentcontent_hotbeautician_rightarrow)
    ImageView ivMainfragmentcontentHotbeauticianRightarrow;
    @BindView(R.id.tv_mainfragmentcontent_hotbeautician_more)
    TextView tvMainfragmentcontentHotbeauticianMore;
    @BindView(R.id.rl_mainfragmentcontent_hotbeautician)
    RelativeLayout rlMainfragmentcontentHotbeautician;
    @BindView(R.id.hlv_mainfragment_beautician)
    RecyclerView hlvMainfragmentBeautician;
    @BindView(R.id.tv_mainfragmentcontent_community_what)
    TextView tvMainfragmentcontentCommunityWhat;
    @BindView(R.id.iv_mainfragmentcontent_community_rightarrow)
    ImageView ivMainfragmentcontentCommunityRightarrow;
    @BindView(R.id.tv_mainfragmentcontent_community_more)
    TextView tvMainfragmentcontentCommunityMore;
    @BindView(R.id.iv_mainfragmentcontent_community_other)
    ImageView ivMainfragmentcontentCommunityOther;
    @BindView(R.id.tv_mainfragmentcontent_community_other)
    TextView tvMainfragmentcontentCommunityOther;
    @BindView(R.id.vw_mainfragmentcontent_community_other)
    View vwMainfragmentcontentCommunityOther;
    @BindView(R.id.rl_mainfragmentcontent_community)
    RelativeLayout rlMainfragmentcontentCommunity;
    @BindView(R.id.mlv_mainfragment_petcircle)
    RecyclerView mlvMainfragmentPetcircle;
    @BindView(R.id.tv_mainfragment_tsfw)
    TextView tvMainfragmentTsfw;
    @BindView(R.id.vw_mainfragment_tsfw)
    View vwMainfragmentTsfw;
    @BindView(R.id.rl_mainfragment_tsfw)
    RelativeLayout rlMainfragmentTsfw;
    @BindView(R.id.tv_mainfragment_tjyy)
    TextView tvMainfragmentTjyy;
    @BindView(R.id.vw_mainfragment_tjyy)
    View vwMainfragmentTjyy;
    @BindView(R.id.rl_mainfragment_tjyy)
    RelativeLayout rlMainfragmentTjyy;
    @BindView(R.id.tv_mainfragment_cwbk)
    TextView tvMainfragmentCwbk;
    @BindView(R.id.vw_mainfragment_cwbk)
    View vwMainfragmentCwbk;
    @BindView(R.id.rl_mainfragment_cwbk)
    RelativeLayout rlMainfragmentCwbk;
    @BindView(R.id.mlv_mainfragment_service)
    RecyclerView mlvMainfragmentService;
    @BindView(R.id.osv_mainfrag)
    SwipeRefreshLayout osvMainfrag;
    Unbinder unbinder;
    private int areaid;
    private int shopid;
    private String areaName;
    private String addressName;
    private String xxAddressName;
    private double lat;
    private double lng;
    private int mypetId;
    private String liveTag;
    private String liveUrl;
    private ArrayList<MainService> listMainService = new ArrayList<MainService>();
    private ArrayList<Beautician> listHotBeautician = new ArrayList<Beautician>();
    private ArrayList<HomeTopMsg> listTopMsg = new ArrayList<HomeTopMsg>();
    private ArrayList<HomeTopMsg> listHotMsg = new ArrayList<HomeTopMsg>();
    private ArrayList<HomeTopMsg> listMsg = new ArrayList<HomeTopMsg>();
    private ArrayList<BannerBean> listBanner1 = new ArrayList<BannerBean>();
    private List<MainPetCircle> listPetCircle = new ArrayList<MainPetCircle>();
    private List<MainCharacteristicService> listCharacteristicService = new ArrayList<MainCharacteristicService>();
    private List<MainHospital> listHospital = new ArrayList<MainHospital>();
    private List<MainPetEncyclopedia> listPetEncyclopedia = new ArrayList<MainPetEncyclopedia>();
    private List<String> localListMsg = new ArrayList<String>();
    private List<String> bannerImgUrls1 = new ArrayList<String>();
    private List<String> bannerImgUrls5 = new ArrayList<String>();
    private ArrayList<BannerBean> listBanner5 = new ArrayList<BannerBean>();
    private String rechargeActivityUrl;
    private int circleId;
    private int circleType;
    private MainServiceAdapter mainServiceAdapter;
    private HotBeauticianAdapter hotBeauticianAdapter;
    private MainPetCircleAdapter mainPetCircleAdapter;
    private MainCharacteristicServiceAdapter mainCharacteristicServiceAdapter;
    private MainPetEncyclopediaAdapter mainPetEncyclopediaAdapter;
    private MainHospitalAdapter mainHospitalAdapter;
    private int bottomIndex = 1;
    private PetCircle circle = new PetCircle();

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Global.LIVEDELAYEDGONE:
                    spUtil.saveBoolean("isExit", false);
                    rlMainfragmentJyzb.setVisibility(View.GONE);
                    break;
                case 1000:
                    closeDialog();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setListener() {
        super.setListener();
        tvMainfragmentcontentTopmsgMsg.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                if (listMsg != null && listMsg.size() > 0 && listMsg.size() > position) {
                    HomeTopMsg homeTopMsg = listMsg.get(position);
                    if (homeTopMsg != null) {
                        if (Utils.isStrNull(homeTopMsg.url)) {
                            UmengStatistics.UmengEventStatistics(mActivity,
                                    Global.UmengEventID.click_HomePage_Headlines);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", homeTopMsg.url);
                            bundle.putInt("previous", Global.TOPMSG_TO_AD);
                            startActivity(ADActivity.class, bundle);
                        }
                    }
                }
            }
        });
        rvpMainfragmentBanner1.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (listBanner1 != null
                        && listBanner1.size() > 0 && listBanner1.size() > position) {
                    BannerBean bannerBean = listBanner1.get(position);
                    if (bannerBean != null) {
                        if (Utils.isStrNull(bannerBean.url)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("url", bannerBean.url);
                            startActivity(ADActivity.class, bundle);
                        } else {
                            Utils.imageZoom(mActivity, 0, bannerImgUrls1.toArray(new String[bannerImgUrls1.size()]));
                        }
                    }
                }
            }
        });
        rvpBanner5.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (listBanner5 != null
                        && listBanner5.size() > 0 && listBanner5.size() > position) {
                    BannerBean bannerBean = listBanner5.get(position);
                    if (bannerBean != null) {
                        if (Utils.isStrNull(bannerBean.url)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("url", bannerBean.url);
                            startActivity(ADActivity.class, bundle);
                        } else {
                            Utils.imageZoom(mActivity, 0, bannerImgUrls5.toArray(new String[bannerImgUrls5.size()]));
                        }
                    }
                }
            }
        });
        osvMainfrag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (areaid > 0) {
                    RequestHomeData();
                }
            }
        });
    }

    @OnClick({R.id.tv_mainfragmentcontent_community_other, R.id.iv_mainfragmentcontent_community_other,
            R.id.rl_mainfragmentcontent_community, R.id.rl_mainfragment_jyzb_delete, R.id.rl_mainfragment_jyzb,
            R.id.rl_mainfragment_tsfw, R.id.rl_mainfragment_tjyy, R.id.rl_mainfragment_cwbk, R.id.tv_mainfragment_areaname,
            R.id.iv_mainfragment_scan,
            R.id.iv_mainfragment_tousu, R.id.rl_mainfragmentcontent_hotbeautician})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mainfragmentcontent_community_other:
                spUtil.saveBoolean(
                        "TAG_vw_mainfragmentcontent_community_other_click", true);
                vwMainfragmentcontentCommunityOther.setVisibility(View.GONE);
                goToCircle();
                break;
            case R.id.iv_mainfragmentcontent_community_other:
                goToCircle();
                break;
            case R.id.rl_mainfragmentcontent_community:// 跳转到社区列表
                UmengStatistics.UmengEventStatistics(mActivity,
                        Global.UmengEventID.click_HomePage_PetRingList);
                MainActivity mActivity1 = (MainActivity) getActivity();
                mActivity1.setSelect(0);
                mActivity1.setTabSelection(1);
                break;
            case R.id.rl_mainfragment_jyzb_delete:
                spUtil.saveBoolean("isExit", false);
                // 直播
                rlMainfragmentJyzb.setVisibility(View.GONE);
                break;
            case R.id.rl_mainfragment_jyzb:
                // 跳转到直播列表界面
                Bundle bundle = new Bundle();
                bundle.putString("url", liveUrl);
                startActivity(PetCircleInsideActivity.class, bundle);
                break;
            case R.id.rl_mainfragment_tsfw:
                bottomIndex = 1;
                setIndex(bottomIndex);
                break;
            case R.id.rl_mainfragment_tjyy:
                bottomIndex = 2;
                setIndex(bottomIndex);
                break;
            case R.id.rl_mainfragment_cwbk:
                bottomIndex = 3;
                setIndex(bottomIndex);
                break;
            case R.id.tv_mainfragment_areaname:
                UmengStatistics.UmengEventStatistics(mActivity,
                        Global.UmengEventID.click_HomePage_AreaSelect);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("areaid", areaid);
                startActivity(SelectServiceAreaActivity.class, bundle1);
                break;
            case R.id.iv_mainfragment_scan:
                UmengStatistics.UmengEventStatistics(mActivity,
                        Global.UmengEventID.click_HomePage_Scan);
                startActivity(MipcaActivityCapture.class);
                break;
            case R.id.iv_mainfragment_tousu:
                UmengStatistics.UmengEventStatistics(mActivity,
                        Global.UmengEventID.click_HomePage_Complaints);
                startActivity(FeedBackActivity.class);
                break;
            case R.id.rl_mainfragmentcontent_hotbeautician:
                UmengStatistics.UmengEventStatistics(mActivity,
                        Global.UmengEventID.click_HomePage_PopularList);
                startActivity(MainToBeauList.class);
                break;
        }
    }

    private void goToCircle() {
        MainActivity mActivity = (MainActivity) getActivity();
        switch (circleType) {
            case 1:
                mActivity.setSelect(1);
                mActivity.setTabSelection(1);
                break;
            case 2:
                mActivity.setSelect(2);
                mActivity.setTabSelection(1);
                break;
            case 3:
                goToPetCircle(circleId);
                break;
            default:
                break;
        }
    }

    private void goToPetCircle(int areaId) {
        circle.PostGroupId = areaId;
        Bundle bundle = new Bundle();
        bundle.putSerializable("petCircle", circle);
        startActivity(PetCircleInsideActivity.class, bundle);
    }

    @Override
    public void setView() {
        super.setView();
        osvMainfrag.setRefreshing(false);
        if (areaName != null && !"".equals(areaName)) {
            tvMainfragmentcontentHotbeautician.setText("热门美容师" + areaName);
        }
        // 设置刷新时动画的颜色，可以设置4个
        osvMainfrag.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        rlMainfragmentJyzb.bringToFront();
        /**
         *icon
         */
        listMainService.clear();
        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);
        GridLayoutManager.setScrollEnabled(false);*/
        gvMainfragmentcontentService.setLayoutManager(new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false));
        mainServiceAdapter = new MainServiceAdapter(mActivity, listMainService, R.layout.mainserviceitem);
        //设置动画
        gvMainfragmentcontentService.setItemAnimator(new DefaultItemAnimator());
        //添加RecyclerView的分割线
        //gvMainfragmentcontentService.addItemDecoration(new DividerItemDecoration(mActivity, GridLayoutManager.VERTICAL));
        gvMainfragmentcontentService.setHasFixedSize(true);
        /**
         *热门美容师
         */
        listHotBeautician.clear();
        hlvMainfragmentBeautician.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        hotBeauticianAdapter = new HotBeauticianAdapter(mActivity, listHotBeautician, R.layout.item_mainfrag_hotbeau);

        //设置动画
        hlvMainfragmentBeautician.setItemAnimator(new DefaultItemAnimator());
        //添加RecyclerView的分割线
        //hlvMainfragmentBeautician.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL));
        hlvMainfragmentBeautician.setHasFixedSize(true);
        /**
         *精选帖子
         */
        listPetCircle.clear();
        LinearLayoutManager petCircleLinearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        //petCircleLinearLayoutManager.setScrollEnabled(false);
        mlvMainfragmentPetcircle.setLayoutManager(petCircleLinearLayoutManager);
        mainPetCircleAdapter = new MainPetCircleAdapter(mActivity, listPetCircle, R.layout.item_mainfrag_petcircle);

        //设置动画
        mlvMainfragmentPetcircle.setItemAnimator(new DefaultItemAnimator());
        //添加RecyclerView的分割线
        mlvMainfragmentPetcircle.addItemDecoration(new DividerItemDecoration(mActivity, petCircleLinearLayoutManager.VERTICAL));
        mlvMainfragmentPetcircle.setHasFixedSize(true);
        /**
         * 底部数据
         */
        LinearLayoutManager serviceLinearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        //serviceLinearLayoutManager.setScrollEnabled(false);
        mlvMainfragmentService.setLayoutManager(serviceLinearLayoutManager);
        //设置动画
        mlvMainfragmentService.setItemAnimator(new DefaultItemAnimator());
        //添加RecyclerView的分割线
        mlvMainfragmentService.addItemDecoration(new DividerItemDecoration(mActivity, serviceLinearLayoutManager.VERTICAL));
        mlvMainfragmentService.setHasFixedSize(true);
        mainCharacteristicServiceAdapter = new MainCharacteristicServiceAdapter(mActivity, listCharacteristicService, R.layout.item_mainfrag_charaservice);
        mainHospitalAdapter = new MainHospitalAdapter(mActivity, listHospital, R.layout.item_mainfrag_hospital);
        mainPetEncyclopediaAdapter = new MainPetEncyclopediaAdapter(mActivity, listPetEncyclopedia, R.layout.item_mainfrag_petencyclopedia);
    }

    @Override
    public void initParms(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        RequestAutoLoginData();
        boolean isRestart = spUtil.getBoolean("isRestart", false);
        if (isRestart && Utils.checkLogin(mActivity)) {// 取上一单的地址，门店信息
            spUtil.saveInt("regionId", spUtil.getInt("upRegionId", -1));
            spUtil.saveInt("tareaid", spUtil.getInt("areaid", 0));
            spUtil.saveInt("tshopid", spUtil.getInt("shopid", 0));
            spUtil.saveString("tareaname", spUtil.getString("upShopName", ""));
            spUtil.saveString("addressName", spUtil.getString("address", ""));
            spUtil.saveString("xxAddressName", "");
        } else {// 取用户保存的地址，门店信息
            spUtil.saveInt("regionId", spUtil.getInt("saveregionId", -1));
            spUtil.saveInt("tareaid", spUtil.getInt("savetareaid", 0));
            spUtil.saveInt("tshopid", spUtil.getInt("savetshopid", 0));
            spUtil.saveString("tareaname",
                    spUtil.getString("savetareaname", ""));
            spUtil.saveString("addressName",
                    spUtil.getString("saveaddressName", ""));
            spUtil.saveString("xxAddressName",
                    spUtil.getString("savexxAddressName", ""));
        }
        areaid = spUtil.getInt("tareaid", 0);
        shopid = spUtil.getInt("tshopid", 0);
        areaid = 8;
        if (areaid == 100) {
            tvMainfragmentcontentHotbeautician.setText("热门美容师");
        } else {
            areaName = spUtil.getString("tareaname", "");
            if (areaName != null && !"".equals(areaName)) {
                tvMainfragmentcontentHotbeautician.setText("热门美容师" + areaName);
            }
        }
        areaName = spUtil.getString("tareaname", "");
        addressName = spUtil.getString("addressName", "");
        xxAddressName = spUtil.getString("xxAddressName", "");
        if (areaid > 0) {
            RequestHomeData();
        }
        setPetAddress();
    }

    /**
     * 自动登录
     */
    private void RequestAutoLoginData() {
        HttpParams params = new HttpParams();
        params.put("id", spUtil.getInt("userid", 0));
        if (lat != 0 || lng != 0) {
            params.put("lat", lat);
            params.put("lng", lng);
        }
        params.put("channelId", Market.getMarketId(mActivity));
        OkGoUtils.Get_String(mActivity, UrlContans.autoLoginUrl, CacheKeyUtils.Key_AutoLoginUrl, params, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtils.e("RequestAutoLoginData response.code() = " + response.code());
                LogUtils.e("RequestAutoLoginData s = " + s);
                if (Utils.isStrNull(s)) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        int resultCode = jobj.getInt("code");
                        String msg = jobj.getString("msg");
                        if (0 == resultCode) {
                            if (jobj.has("data") && !jobj.isNull("data")) {
                                JSONObject jData = jobj.getJSONObject("data");
                                if (jData.has("user") && !jData.isNull("user")) {
                                    JSONObject jUser = jData.getJSONObject("user");
                                    if (jUser.has("areacode")
                                            && !jUser.isNull("areacode")) {
                                        spUtil.saveInt("upRegionId",
                                                jUser.getInt("areacode"));
                                    } else {
                                        spUtil.removeData("upRegionId");
                                    }
                                    if (jUser.has("shopName")
                                            && !jUser.isNull("shopName")) {
                                        spUtil.saveString("upShopName",
                                                jUser.getString("shopName"));
                                    } else {
                                        spUtil.removeData("upShopName");
                                    }
                                    if (jUser.has("shopId") && !jUser.isNull("shopId")) {
                                        spUtil.saveInt("shopid", jUser.getInt("shopId"));
                                    } else {
                                        spUtil.removeData("shopid");
                                    }
                                    if (jUser.has("areaId") && !jUser.isNull("areaId")) {
                                        spUtil.saveInt("areaid", jUser.getInt("areaId"));
                                    } else {
                                        spUtil.removeData("areaid");
                                    }
                                    if (jUser.has("areaName")
                                            && !jUser.isNull("areaName")) {
                                        spUtil.saveString("areaname",
                                                jUser.getString("areaName"));
                                    } else {
                                        spUtil.removeData("areaname");
                                    }
                                    if (jUser.has("homeAddress")
                                            && !jUser.isNull("homeAddress")) {
                                        JSONObject jAddr = jUser
                                                .getJSONObject("homeAddress");
                                        if (jAddr.has("Customer_AddressId")
                                                && !jAddr.isNull("Customer_AddressId")) {
                                            spUtil.saveInt("addressid",
                                                    jAddr.getInt("Customer_AddressId"));
                                        }
                                        if (jAddr.has("lat") && !jAddr.isNull("lat")) {
                                            spUtil.saveString("lat",
                                                    jAddr.getDouble("lat") + "");
                                        }
                                        if (jAddr.has("lng") && !jAddr.isNull("lng")) {
                                            spUtil.saveString("lng",
                                                    jAddr.getDouble("lng") + "");
                                        }
                                        if (jAddr.has("address")
                                                && !jAddr.isNull("address")) {
                                            spUtil.saveString("address",
                                                    jAddr.getString("address"));
                                        }
                                    } else {
                                        spUtil.removeData("addressid");
                                        spUtil.removeData("lat");
                                        spUtil.removeData("lng");
                                        spUtil.removeData("address");
                                    }
                                    if (jUser.has("pet") && !jUser.isNull("pet")) {
                                        JSONObject jPet = jUser.getJSONObject("pet");
                                        if (jPet.has("isCerti")
                                                && !jPet.isNull("isCerti")) {
                                            spUtil.saveInt("isCerti",
                                                    jPet.getInt("isCerti"));
                                        }
                                        if (jPet.has("mypetId") && !jPet.isNull("mypetId")) {
                                            mypetId = jPet.getInt("mypetId");
                                        } else {
                                            mypetId = 0;
                                        }
                                    } else {
                                        spUtil.removeData("isCerti");
                                    }
                                    if (jUser.has("myPet") && !jUser.isNull("myPet")) {
                                        JSONObject jMyPet = jUser
                                                .getJSONObject("myPet");
                                        if (mypetId > 0 && jMyPet.has("petId") && !jMyPet.isNull("petId")) {
                                            spUtil.saveInt("petid",
                                                    jMyPet.getInt("petId"));
                                        }
                                        if (jMyPet.has("CustomerPetId")
                                                && !jMyPet.isNull("CustomerPetId")) {
                                            spUtil.saveInt("customerpetid",
                                                    jMyPet.getInt("CustomerPetId"));
                                        } else {
                                            spUtil.removeData("customerpetid");
                                        }

                                        if (jMyPet.has("CustomerPetId")
                                                && !jMyPet.isNull("CustomerPetId")) {
                                            spUtil.saveInt("customerpetid",
                                                    jMyPet.getInt("CustomerPetId"));
                                        } else {
                                            spUtil.removeData("customerpetid");
                                        }

                                        if (jMyPet.has("nickName")
                                                && !jMyPet.isNull("nickName")) {
                                            spUtil.saveString("customerpetname",
                                                    jMyPet.getString("nickName"));
                                        } else {
                                            spUtil.removeData("customerpetname");
                                        }
                                        if (jMyPet.has("avatarPath")
                                                && !jMyPet.isNull("avatarPath")) {
                                            String mypetImage = jMyPet
                                                    .getString("avatarPath");
                                            if (!mypetImage.startsWith("http://")
                                                    && !mypetImage
                                                    .startsWith("https://")) {
                                                spUtil.saveString(
                                                        "mypetImage",
                                                        UrlContans.getBaseUrl()
                                                                + jMyPet.getString("avatarPath"));
                                            } else {
                                                spUtil.saveString("mypetImage",
                                                        jMyPet.getString("avatarPath"));
                                            }
                                        } else {
                                            spUtil.removeData("mypetImage");
                                        }
                                    } else {
                                        spUtil.removeData("customerpetname");
                                        spUtil.removeData("customerpetid");
                                        spUtil.removeData("mypetImage");

                                    }
                                    if (jUser.has("live") && !jUser.isNull("live")) {
                                        JSONObject jlive = jUser.getJSONObject("live");
                                        if (jlive.has("tag") && !jlive.isNull("tag")) {
                                            liveTag = jlive.getString("tag");
                                            boolean isExit = spUtil.getBoolean(
                                                    "isExit", false);
                                            if (!TextUtils.isEmpty(liveTag)) {
                                                if (isExit) {
                                                    tvMainfragmentJyzbName
                                                            .setText(liveTag);
                                                    rlMainfragmentJyzb
                                                            .setVisibility(View.VISIBLE);
                                                    mHandler.sendEmptyMessageDelayed(
                                                            Global.LIVEDELAYEDGONE,
                                                            5000);
                                                } else {
                                                    rlMainfragmentJyzb
                                                            .setVisibility(View.GONE);
                                                }
                                            } else {
                                                rlMainfragmentJyzb
                                                        .setVisibility(View.GONE);
                                            }
                                        }
                                        if (jlive.has("url") && !jlive.isNull("url")) {
                                            liveUrl = jlive.getString("url");
                                        }
                                    }
                                }
                            }
                        } else {
                            if (Utils.isStrNull(msg)) {
                                ToastUtil.showToastShortBottom(mActivity, msg);
                            }
                        }
                    } catch (JSONException e) {
                        ToastUtil.showToastShortBottom(mActivity, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showToastShortBottom(mActivity, "请求失败");
            }
        });
    }

    /**
     * 首页数据
     */
    private void RequestHomeData() {
        HttpParams params = new HttpParams();
        params.put("time", System.currentTimeMillis());
        params.put("page", 1);
        if (areaid > 0 && areaid != 100) {
            params.put("areaId", areaid);
        }
        params.put("time", System.currentTimeMillis());
        OkGoUtils.Get_String(mActivity, UrlContans.homePageUrl, CacheKeyUtils.Key_HomePageUrl, params, new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                osvMainfrag.setRefreshing(false);
                LogUtils.e("RequestHomeData response.code() = " + response.code());
                LogUtils.e("RequestHomeData s = " + s);
                if (Utils.isStrNull(s)) {
                    try {
                        JSONObject jobj = new JSONObject(s);
                        int code = jobj.getInt("code");
                        String msg = jobj.getString("msg");
                        if (code == 0) {
                            if (jobj.has("data") && !jobj.isNull("data")) {
                                JSONObject jdata = jobj.getJSONObject("data");
                                if (jdata.has("hpcBannerList")
                                        && !jdata.isNull("hpcBannerList")) {
                                    JSONArray jarrPetCircle = jdata
                                            .getJSONArray("hpcBannerList");
                                    if (jarrPetCircle.length() > 0) {
                                        listPetCircle.clear();
                                        for (int i = 0; i < jarrPetCircle.length(); i++) {
                                            listPetCircle.add(MainPetCircle
                                                    .json2Entity(jarrPetCircle
                                                            .getJSONObject(i)));
                                        }
                                        mlvMainfragmentPetcircle
                                                .setVisibility(View.VISIBLE);
                                        mlvMainfragmentPetcircle.setAdapter(mainPetCircleAdapter);
                                    }
                                }
                                if (jdata.has("homePageCopy")
                                        && !jdata.isNull("homePageCopy")) {
                                    JSONObject jobjHomePageCopy = jdata
                                            .getJSONObject("homePageCopy");
                                    if (jobjHomePageCopy.has("1")
                                            && !jobjHomePageCopy.isNull("1")) {
                                        String string = jobjHomePageCopy.getString("1");
                                        Utils.setText(tvMainfragmentTsfw, string, "",
                                                View.VISIBLE, View.INVISIBLE);
                                    }
                                    if (jobjHomePageCopy.has("2")
                                            && !jobjHomePageCopy.isNull("2")) {
                                        String string = jobjHomePageCopy.getString("2");
                                        Utils.setText(tvMainfragmentTjyy, string, "",
                                                View.VISIBLE, View.INVISIBLE);
                                    }
                                    if (jobjHomePageCopy.has("3")
                                            && !jobjHomePageCopy.isNull("3")) {
                                        String string = jobjHomePageCopy.getString("3");
                                        Utils.setText(tvMainfragmentCwbk, string, "",
                                                View.VISIBLE, View.INVISIBLE);
                                    }
                                }
                                if (jdata.has("services") && !jdata.isNull("services")) {
                                    JSONArray jarrCharacteristicService = jdata
                                            .getJSONArray("services");
                                    if (jarrCharacteristicService.length() > 0) {
                                        listCharacteristicService.clear();
                                        for (int i = 0; i < jarrCharacteristicService
                                                .length(); i++) {
                                            listCharacteristicService
                                                    .add(MainCharacteristicService
                                                            .json2Entity(jarrCharacteristicService
                                                                    .getJSONObject(i)));
                                        }
                                    }
                                }
                                if (jdata.has("hospitals") && !jdata.isNull("hospitals")) {
                                    JSONArray jarrHospital = jdata
                                            .getJSONArray("hospitals");
                                    if (jarrHospital.length() > 0) {
                                        listHospital.clear();
                                        for (int i = 0; i < jarrHospital.length(); i++) {
                                            listHospital.add(MainHospital
                                                    .json2Entity(jarrHospital
                                                            .getJSONObject(i)));
                                        }
                                    }
                                }
                                if (jdata.has("knowledges") && !jdata.isNull("knowledges")) {
                                    JSONArray jarrPetEncyclopedia = jdata
                                            .getJSONArray("knowledges");
                                    if (jarrPetEncyclopedia.length() > 0) {
                                        listPetEncyclopedia.clear();
                                        for (int i = 0; i < jarrPetEncyclopedia.length(); i++) {
                                            listPetEncyclopedia.add(MainPetEncyclopedia
                                                    .json2Entity(jarrPetEncyclopedia
                                                            .getJSONObject(i)));
                                        }
                                    }
                                }
                                setIndex(bottomIndex);
                                listMsg.clear();
                                if (jdata.has("newHeadLind")
                                        && !jdata.isNull("newHeadLind")) {
                                    JSONArray jtopmsgarr = jdata
                                            .getJSONArray("newHeadLind");
                                    if (jtopmsgarr.length() > 0) {
                                        listTopMsg.clear();
                                        for (int i = 0; i < jtopmsgarr.length(); i++) {
                                            listTopMsg.add(HomeTopMsg
                                                    .json2Entity(jtopmsgarr
                                                            .getJSONObject(i)));
                                        }
                                        for (int j = 0; j < listTopMsg.size(); j++) {
                                            HomeTopMsg homeTopMsg = listTopMsg.get(j);
                                            if (homeTopMsg != null) {
                                                homeTopMsg.setTitle("【最新】 "
                                                        + homeTopMsg.getTitle());
                                            }
                                        }
                                        listMsg.addAll(listTopMsg);
                                    }
                                }
                                if (jdata.has("hotHeadLind")
                                        && !jdata.isNull("hotHeadLind")) {
                                    JSONArray jhotmsgarr = jdata
                                            .getJSONArray("hotHeadLind");
                                    if (jhotmsgarr.length() > 0) {
                                        listHotMsg.clear();
                                        for (int i = 0; i < jhotmsgarr.length(); i++) {
                                            listHotMsg.add(HomeTopMsg
                                                    .json2Entity(jhotmsgarr
                                                            .getJSONObject(i)));
                                        }
                                        for (int j = 0; j < listHotMsg.size(); j++) {
                                            HomeTopMsg homeTopMsg = listHotMsg.get(j);
                                            if (homeTopMsg != null) {
                                                homeTopMsg.setTitle("【最热】 "
                                                        + homeTopMsg.getTitle());
                                            }
                                        }
                                        listMsg.addAll(listHotMsg);
                                    }
                                }
                                LogUtils.e("listMsg.size() = "+listMsg.size());
                                LogUtils.e("listMsg.toString() = "+listMsg.toString());
                                if (listMsg.size() > 0) {
                                    localListMsg.clear();
                                    for (int i = 0; i < listMsg.size(); i++) {
                                        localListMsg.add(listMsg.get(i).getTitle());
                                    }
                                    LogUtils.e("localListMsg.size() = "+localListMsg.size());
                                    LogUtils.e("localListMsg.toString() = "+localListMsg.toString());
                                    tvMainfragmentcontentTopmsgMsg.startWithList(localListMsg);
                                    rlMainfragmentcontentTopmsg.setVisibility(View.VISIBLE);
                                } else {
                                    rlMainfragmentcontentTopmsg.setVisibility(View.GONE);
                                }
                                if (jdata.has("icons") && !jdata.isNull("icons")) {
                                    JSONArray jiconarr = jdata.getJSONArray("icons");
                                    if (jiconarr.length() > 0) {
                                        listMainService.clear();
                                        for (int i = 0; i < jiconarr.length(); i++) {
                                            JSONObject jicon = jiconarr.getJSONObject(i);
                                            MainService ms = new MainService();
                                            if (jicon.has("pic") && !jicon.isNull("pic")) {
                                                ms.strimg = jicon.getString("pic");
                                            }
                                            if (jicon.has("txt") && !jicon.isNull("txt")) {
                                                ms.name = jicon.getString("txt");
                                            }
                                            if (jicon.has("url") && !jicon.isNull("url")) {
                                                ms.url = jicon.getString("url");
                                            }
                                            if (jicon.has("point")
                                                    && !jicon.isNull("point")) {
                                                ms.point = jicon.getInt("point");
                                            }
                                            if (jicon.has("newOrHot")
                                                    && !jicon.isNull("newOrHot")) {
                                                ms.newOrHot = jicon.getInt("newOrHot");
                                            }
                                            listMainService.add(i, ms);
                                        }
                                        gvMainfragmentcontentService.setAdapter(mainServiceAdapter);
                                    }
                                }
                                listHotBeautician.clear();
                                if (jdata.has("topWorkers")
                                        && !jdata.isNull("topWorkers")
                                        && Utils.JSON_TYPE.JSON_TYPE_OBJECT
                                        .equals(Utils.getJSONType(jdata
                                                .getString("topWorkers")))) {
                                    JSONObject topWorkersjsonObject = jdata
                                            .getJSONObject("topWorkers");
                                    if (topWorkersjsonObject.has("workers")
                                            && !topWorkersjsonObject.isNull("workers")) {
                                        JSONArray workersjservicearr = topWorkersjsonObject
                                                .getJSONArray("workers");
                                        if (workersjservicearr.length() > 0) {
                                            listHotBeautician.clear();
                                            for (int i = 0; i < workersjservicearr.length(); i++) {
                                                Beautician btc = Beautician
                                                        .json2Entity(workersjservicearr
                                                                .getJSONObject(i));
                                                if (i <= 2) {
                                                    btc.colorId = i;
                                                } else {
                                                    if (listHotBeautician.get(i - 1).colorId == 0) {
                                                        btc.colorId = 1;
                                                    } else if (listHotBeautician.get(i - 1).colorId == 1) {
                                                        btc.colorId = 2;
                                                    } else if (listHotBeautician.get(i - 1).colorId == 2) {
                                                        btc.colorId = 0;
                                                    }
                                                }
                                                listHotBeautician.add(btc);
                                            }
                                            rlMainfragmentcontentHotbeautician.setVisibility(View.VISIBLE);
                                            hlvMainfragmentBeautician
                                                    .setVisibility(View.VISIBLE);
                                            hlvMainfragmentBeautician.setAdapter(hotBeauticianAdapter);
                                        }
                                    }
                                } else {
                                    rlMainfragmentcontentHotbeautician.setVisibility(View.GONE);
                                    hlvMainfragmentBeautician.setVisibility(View.GONE);
                                }
                                if (jdata.has("banner1") && !jdata.isNull("banner1")) {
                                    JSONArray jbanner2arr = jdata.getJSONArray("banner1");
                                    if (jbanner2arr.length() > 0) {
                                        rvpMainfragmentBanner1.setVisibility(View.VISIBLE);
                                        listBanner1.clear();
                                        bannerImgUrls1.clear();
                                        for (int i = 0; i < jbanner2arr.length(); i++) {
                                            listBanner1.add(BannerBean.json2Entity(jbanner2arr
                                                    .getJSONObject(i)));
                                        }
                                        for (int i = 0; i < listBanner1.size(); i++) {
                                            BannerBean bannerBean = listBanner1.get(i);
                                            if (bannerBean != null) {
                                                bannerImgUrls1.add(bannerBean.imgurl);
                                            }
                                        }
                                        Utils.startBanner(rvpMainfragmentBanner1, BannerConfig.CIRCLE_INDICATOR, bannerImgUrls1, Transformer.ScaleInOut, null, true, 3000, BannerConfig.RIGHT);
                                    } else {
                                        rvpMainfragmentBanner1.setVisibility(View.GONE);
                                    }
                                } else {
                                    rvpMainfragmentBanner1.setVisibility(View.GONE);
                                }
                                if (jdata.has("banner5") && !jdata.isNull("banner5")) {
                                    JSONArray jbanner5arr = jdata.getJSONArray("banner5");
                                    if (jbanner5arr.length() > 0) {
                                        listBanner5.clear();
                                        bannerImgUrls5.clear();
                                        rvpBanner5.setVisibility(View.VISIBLE);
                                        for (int i = 0; i < jbanner5arr.length(); i++) {
                                            listBanner5.add(BannerBean.json2Entity(jbanner5arr
                                                    .getJSONObject(i)));
                                        }
                                        for (int i = 0; i < listBanner5.size(); i++) {
                                            BannerBean bannerBean = listBanner5.get(i);
                                            if (bannerBean != null) {
                                                bannerImgUrls5.add(bannerBean.imgurl);
                                            }
                                        }
                                        Utils.startBanner(rvpBanner5, BannerConfig.CIRCLE_INDICATOR, bannerImgUrls5, Transformer.ScaleInOut, null, true, 3000, BannerConfig.RIGHT);
                                    } else {
                                        rvpBanner5.setVisibility(View.GONE);
                                    }
                                } else {
                                    rvpBanner5.setVisibility(View.GONE);
                                }
                                rlMainfragmentcontentCommunity
                                        .setVisibility(View.VISIBLE);

                                if (jdata.has("homePostCustomInfo")
                                        && !jdata.isNull("homePostCustomInfo")) {
                                    ivMainfragmentcontentCommunityOther
                                            .setVisibility(View.VISIBLE);
                                    ivMainfragmentcontentCommunityOther
                                            .setVisibility(View.VISIBLE);
                                    JSONObject homePostCustomInfo = jdata
                                            .getJSONObject("homePostCustomInfo");
                                    if (homePostCustomInfo.has("circleId")
                                            && !homePostCustomInfo.isNull("circleId")) {
                                        circleId = homePostCustomInfo.getInt("circleId");
                                    }
                                    if (homePostCustomInfo.has("img")
                                            && !homePostCustomInfo.isNull("img")) {
                                        if (homePostCustomInfo.has("title")
                                                && !homePostCustomInfo.isNull("title")) {
                                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                                    120, 55);
                                            layoutParams.rightMargin = 20;
                                            ivMainfragmentcontentCommunityOther
                                                    .setLayoutParams(layoutParams);
                                        } else {
                                            ivMainfragmentcontentCommunityOther
                                                    .setLayoutParams(new LinearLayout.LayoutParams(
                                                            LinearLayout.LayoutParams.MATCH_PARENT, 55));
                                        }
                                        ivMainfragmentcontentCommunityOther
                                                .setVisibility(View.VISIBLE);
                                        String img = homePostCustomInfo.getString("img");
                                        GlideUtils.loadNetImg(mActivity, img, R.drawable.icon_production_default, R.drawable.icon_production_default, null, ivMainfragmentcontentCommunityOther);
                                    } else {
                                        ivMainfragmentcontentCommunityOther
                                                .setVisibility(View.GONE);
                                    }
                                    if (homePostCustomInfo.has("title")
                                            && !homePostCustomInfo.isNull("title")) {
                                        boolean TAG_vw_mainfragmentcontent_community_other_click = spUtil
                                                .getBoolean(
                                                        "TAG_vw_mainfragmentcontent_community_other_click",
                                                        false);
                                        if (!TAG_vw_mainfragmentcontent_community_other_click) {
                                            vwMainfragmentcontentCommunityOther
                                                    .setVisibility(View.VISIBLE);
                                        }
                                        vwMainfragmentcontentCommunityOther
                                                .setVisibility(View.VISIBLE);
                                        String title = homePostCustomInfo
                                                .getString("title");
                                        Utils.setStringTextGone(
                                                tvMainfragmentcontentCommunityOther,
                                                title);
                                    } else {
                                        tvMainfragmentcontentCommunityOther
                                                .setVisibility(View.GONE);
                                    }
                                    if (homePostCustomInfo.has("type")
                                            && !homePostCustomInfo.isNull("type")) {
                                        circleType = homePostCustomInfo.getInt("type");
                                    }
                                } else {
                                    tvMainfragmentcontentCommunityOther
                                            .setVisibility(View.GONE);
                                    tvMainfragmentcontentCommunityOther
                                            .setVisibility(View.GONE);
                                }
                                if (jdata.has("rechargeActivityUrl")
                                        && !jdata.isNull("rechargeActivityUrl")) {
                                    rechargeActivityUrl = jdata
                                            .getString("rechargeActivityUrl");
                                    if (rechargeActivityUrl != null
                                            && !TextUtils.isEmpty(rechargeActivityUrl)) {
                                        boolean TAG_MAINGOTOH5 = spUtil.getBoolean(
                                                "TAG_MAINGOTOH5", false);
                                        if (!TAG_MAINGOTOH5) {
                                            // showPopPhoto();//原活动界面
                                            spUtil.saveBoolean("TAG_MAINGOTOH5", true);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (Utils.isStrNull(msg)) {
                                ToastUtil.showToastShortCenter(mActivity,
                                        jobj.getString("msg"));
                            }
                        }
                        mHandler.sendEmptyMessageDelayed(1000, 800);
                    } catch (Exception e) {
                        mHandler.sendEmptyMessageDelayed(1000, 800);
                        e.printStackTrace();
                        ToastUtil.showToastShortBottom(mActivity, "数据异常");
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                osvMainfrag.setRefreshing(false);
                ToastUtil.showToastShortBottom(mActivity, "请求失败");
            }
        });
    }

    private void setPetAddress() {
        if (Utils.isStrNull(addressName)) {
            tvMainfragmentAreaname.setText(addressName + xxAddressName);
        } else {
            if (Utils.isStrNull(areaName)) {
                tvMainfragmentAreaname.setText("宠物位置：" + areaName);
            } else {
                tvMainfragmentAreaname.setText("请输入宠物地址");
            }
        }
    }

    private void setIndex(int num) {
        switch (num) {
            case 1:
                tvMainfragmentTsfw.setTextColor(getResources().getColor(
                        R.color.aD1494F));
                tvMainfragmentTjyy.setTextColor(getResources().getColor(
                        R.color.black));
                tvMainfragmentCwbk.setTextColor(getResources().getColor(
                        R.color.black));
                vwMainfragmentTsfw.setVisibility(View.VISIBLE);
                vwMainfragmentTjyy.setVisibility(View.GONE);
                vwMainfragmentCwbk.setVisibility(View.GONE);
                mlvMainfragmentService.setAdapter(mainCharacteristicServiceAdapter);
                break;
            case 2:
                tvMainfragmentTjyy.setTextColor(getResources().getColor(
                        R.color.aD1494F));
                tvMainfragmentTsfw.setTextColor(getResources().getColor(
                        R.color.black));
                tvMainfragmentCwbk.setTextColor(getResources().getColor(
                        R.color.black));
                vwMainfragmentTjyy.setVisibility(View.VISIBLE);
                vwMainfragmentTsfw.setVisibility(View.GONE);
                vwMainfragmentCwbk.setVisibility(View.GONE);
                mlvMainfragmentService.setAdapter(mainHospitalAdapter);
                break;
            case 3:
                tvMainfragmentCwbk.setTextColor(getResources().getColor(
                        R.color.aD1494F));
                tvMainfragmentTjyy.setTextColor(getResources().getColor(
                        R.color.black));
                tvMainfragmentTsfw.setTextColor(getResources().getColor(
                        R.color.black));
                vwMainfragmentCwbk.setVisibility(View.VISIBLE);
                vwMainfragmentTjyy.setVisibility(View.GONE);
                vwMainfragmentTsfw.setVisibility(View.GONE);
                mlvMainfragmentService.setAdapter(mainPetEncyclopediaAdapter);
                break;

            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        tvMainfragmentcontentTopmsgMsg.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        tvMainfragmentcontentTopmsgMsg.stopFlipping();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
