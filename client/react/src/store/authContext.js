// import React, { useState, useEffect, useCallback } from "react";
// import * as authAction from './authAction'; 

// let logoutTimer;

// //각각의 객체 만듦
// const AuthContext = React.createContext({
//   token: '',
//   userObj: { email: '', nickname: '' },
//   isLoggedIn: false,
//   isSuccess: false,
//   isGetSuccess: false,
//   signup: (email, password, nickname) => {},
//   login: (email,password) => {},
//   logout: () => {},
//   getUser: () => {},
 
// });

// //tokenData token을 확인하는 함수 실행하여 안의 값을 넣어줌
// export const AuthContextProvider = (props) => {
// const tokenData=authAction.retrieveStoredToken();
// let initialToken;
// if(tokenData){
//  initialToken=tokenData.token;
// }
// }
// const [token,setToken]=useState(initialToken);
// const [userObj,setUserObj]=useState({email:'',nickname:''});
// const [isSuccess,setIsSuccess]=useState(false);
// const [isGetSuccess,setIsGetSuccess]=useState(false);

// const userIsLoggedIn=!!token;


// //회원가입 하는 함수
// //  const signupHandler=(email,password,nickname)=>{
// // setIsSuccess(false);
// //  const response=authAction.signupActionHandler(email,password,nickname);
// //  response.then((result)=>{
// //  if(result !==null){
// //     setIsSuccess(true);
// //  }
// //  });

// //  };
// //로그인 하는 함수
//  const loginHandler=(email,password)=>{
//     setIsSuccess(false);
//  console.log(isSuccess);

//  const data=authAction.loginActionHander(email,password)
//  data.then((result)=>{
//  if(result!==null){
//  const loginData=result.data;
//  setToken(loginData.accessToken)
//  logoutTimer=setTimeout(
//  logoutHandler,
//  authAction.loginTokenHandler(loginData.accessToken.loginDAta.tokenExpiresIn)
//  );
//  setIsSuccess(true);
//  console.log(isSuccess);
//  }})};

// //로그아웃하는 함수
//  const logoutHandler=useCallback(()=>{
//  setToken('');
//  authAction.logoutActinHanlder();
//  if(logoutTimer){
//  clearTimeout(logoutTimer);

//  }
//  },[])

 
//  const getUserHandler =()=>{
//     setIsGetSuccess(false)
//  const data =authAction.getUserAcionHanler(token)
//  data.then((result)=>{
//  if(result!==null){
//  console.log('get user start!');
//  const userData=result.data;
//  setUserObj(userData)
//  setIsGetSuccess(true)

//  }

//  })


//  };



// const chageNickameHAndler=(nickname)=>{

// setIsSuccess(false)

// const data=authAction.changeNciknameAcitonHanlder(nickname,token)
// data.then((result)=>{

// if(result!=null){

// const userData=result.datat
// setUserObj(userdata)
// setIsSuccess(truue)
// }
// })
// };


