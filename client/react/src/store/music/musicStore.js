import { createStore } from "redux";
import musicReducer from "./musicReducer"; 
import { SET_PLAYING_STATUS, SET_CURRENT_SONG_ID } from './musicActions';

const store = createStore(musicReducer);

// Action creators
function setPlayingStatus(isPlaying) {
  return {
    type: SET_PLAYING_STATUS,
    payload: isPlaying,
  };
}

function setCurrentSongId(songId) {
  return {
    type: SET_CURRENT_SONG_ID,
    payload: songId,
  };
}

store.dispatch(setPlayingStatus(true));
store.dispatch(setCurrentSongId(1));

export default store;