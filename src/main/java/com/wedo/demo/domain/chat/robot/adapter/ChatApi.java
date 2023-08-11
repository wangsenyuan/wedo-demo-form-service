package com.wedo.demo.domain.chat.robot.adapter;

import com.wedo.demo.domain.chat.dto.ChatMessage;
import com.wedo.demo.dto.common.R;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatApi {

    @POST("/api/chat")
    Call<R<ChatMessage>> chat(@Body ChatMessage dto);
}
