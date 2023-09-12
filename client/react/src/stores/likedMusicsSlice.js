/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 음악 플레이 목록 관련 리덕스
*/

import { createSlice } from '@reduxjs/toolkit';

export const likedMusicsSlice = createSlice({
  name: 'likedMusics',
  initialState: [],
  reducers: {
    setLikedMusics: (state, action) => {
      return action.payload;
    },
  },
});

// export const { setLikedMusics } = likedMusicsSlice.actions;

export default likedMusicsSlice.reducer;
