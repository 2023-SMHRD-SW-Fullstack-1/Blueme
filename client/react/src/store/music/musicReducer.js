import { SET_MUSIC_IDS, SET_PLAYING_STATUS, SET_CURRENT_SONG_ID } from './musicActions';

const initialState = {
  musicIds: [],
  isPlaying: false,
  currentSongId: null,
};

function music(state = initialState, action) {
  switch (action.type) {
    case SET_MUSIC_IDS:
      return { ...state, musicIds: action.payload };
    case SET_PLAYING_STATUS:
      return { ...state, isPlaying: action.payload };
    case SET_CURRENT_SONG_ID:
      return { ...state, currentSongId: action.payload };
    default:
      return state;
  }
}

export default music;