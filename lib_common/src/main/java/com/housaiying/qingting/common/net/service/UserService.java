package com.housaiying.qingting.common.net.service;

import com.housaiying.qingting.common.bean.UserBean;
import com.housaiying.qingting.common.net.Constans;
import com.housaiying.qingting.common.net.dto.GitHubDTO;
import com.housaiying.qingting.common.net.dto.LoginDTO;
import com.housaiying.qingting.common.net.dto.ResponseDTO;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/12 11:16<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:用户模块Api
 */
public interface UserService {

    /**
     * 登陆
     *
     * @param loginDTO
     * @return
     */
    @Headers(Constans.HEADER_HOST1)
    @POST("app/tokenlogin")
    Observable<ResponseDTO<UserBean>> login(@Body LoginDTO loginDTO);

    /**
     * 获取GitHub数据
     *
     * @return
     */
    @GET(Constans.GITHUB_URL)
    Observable<GitHubDTO> getGitHub();
}
