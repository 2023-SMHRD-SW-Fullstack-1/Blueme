import memberReducer from './member/memberReducer'
import { combineReducers } from 'redux'

const rootReducer = combineReducers({
    memberReducer
})


export default rootReducer;