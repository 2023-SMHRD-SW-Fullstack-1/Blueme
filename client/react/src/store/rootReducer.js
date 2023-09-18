import memberReducer from './member/memberReducer'
import { combineReducers } from 'redux'
import musicReducer from './music/musicReducer';
const rootReducer = combineReducers({
    memberReducer,
    musicReducer
})


export default rootReducer;