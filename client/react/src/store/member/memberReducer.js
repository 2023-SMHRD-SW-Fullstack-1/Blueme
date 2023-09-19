/*
작성자: 이유영
날짜(수정포함): 2023-09-15
설명: 회원관련 리듀서 정의
*/
import {LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT_SUCCESS, USER_DELETE, 
  USER_UPDATE,
  JOIN_REQUEST,
  JOIN_SUCCESS,
  JOIN_FAILURE} from './memberAction'
  
  // 3. Reducers
  const initialState = {
    loading: false,
    user: {
      id : 0,
      email : null,
      password : null,
      nickname : null,
      img_url : null,
      platFormType : null

    },
    error: null,
    isLogin : false
  };
  
 const memberReducer = (state=initialState, action) => {
   switch(action.type){
     case JOIN_REQUEST:
      return {...state, loading:true, isLogin:false};
     case JOIN_SUCCESS:
      return {...state, loading:false, user:action.payload, isLogin:false};
     case JOIN_FAILURE:
      return {...state, loading:false, error:action.payload, isLogin:false};
     case LOGIN_REQUEST:
       return {...state, loading:true, isLogin:false};
     case LOGIN_SUCCESS:
       return {...state, loading:false, user:action.payload, isLogin:true};
     case LOGIN_FAILURE:
       return {...state, loading:false, error:action.payload, isLogin:false};
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
  


