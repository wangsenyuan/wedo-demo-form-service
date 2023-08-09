package com.wedo.demo.domain.chat.adapter;

import com.wedo.demo.domain.chat.dto.ChatMessageDto;
import com.wedo.demo.dto.common.R;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatApi {

    @POST("/api/chat")
    Call<R<ChatMessageDto>> chat(@Body ChatMessageDto dto);
}
