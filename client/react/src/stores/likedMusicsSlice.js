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
