import {LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT_SUCCESS, USER_DELETE, 
  USER_UPDATE} from './memberAction'
  
  // 3. Reducers
  const initialState = {
    loading: false,
    user: {
      id : 0,
      email : null,
      nickname : null,
      img_url : null
    },
    error: null,
    isLogin : false
  };
  
 const memberReducer = (state=initialState, action) => {
   switch(action.type){
     case LOGIN_REQUEST:
       return {...state, loading:true, isLogin:false};
     case LOGIN_SUCCESS:
       return {...state, loading:false, user:action.payload, isLogin:true};
     case LOGIN_FAILURE:
       return {...state, loading:false, error :action.payload, isLogin:false};
     case LOGOUT_SUCCESS:
       return initialState
     case USER_DELETE:
       return initialState
     case USER_UPDATE:
       return {...state, loading:false, user:action.payload, isLogin:true};
     default:
       return state;
   }
  };

  export default memberReducer
  


