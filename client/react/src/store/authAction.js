// import { GET, POST }  from "./axiosState";

// //토큰 생성하는 함수
// // const createTokenHeader = (token) => {
// //   return {
// //     headers: {
// //       'Authorization': 'Bearer ' + token
// //     }
// //   }
// // }
// //토큰 만료시간 계산하는 함수
// const calculateRemainingTime = (expirationTime) => {
//   const currentTime = new Date().getTime();
//   const adjExpirationTime = new Date(expirationTime).getTime();
//   const remainingDuration = adjExpirationTime - currentTime;
  
//   return remainingDuration;
// };
// //토큰값과 만료시간을 받아 localStorage 내부에 저장해주는 함수
// export const loginTokenHandler = (token, expirationTime) => {
//   localStorage.setItem('token', token);
//   localStorage.setItem('expirationTime', String(expirationTime));

//   const remainingTime = calculateRemainingTime(expirationTime);
  
//   return remainingTime;
// }
// //localStorage에 토큰 존재하는 지 검색하는 함수(1초 아래 자동 삭제)
// export const retrieveStoredToken = () => {
//    const storedToken = localStorage.getItem('token');
//    const storedExpirationDate = localStorage.getItem('expirationTime') || '0';

//    const remaingTime = calculateRemainingTime(+storedExpirationDate);

//    if(remaingTime <=1000){
//        localStorage.removeItem('token');
//        localStorage.removeItem('expirationTimed')
//        return null
//    }

// return{
//     token: storedToken,
//     duration: remaingTime
// }
// }
// //회원가입
// // export const signupActionHandler =(email,password,nickname)=>{
// // const URL='/auth/signup'
// // const signupObject={email,password,nickname};

// // const response=POST(URL,signupObject,{});
// // return response;

// // };

// //로그인
// export const loginActionHandler=(email,password)=>{
//     const URL='/login';
//     const loginObject={email,password};
//     const response=POST(URL,loginObject,{});

// return response;

// };
// //로그아웃
// export const logoutActionHandler=()=>{
// localStorage.removeItem('token');
// localStorage.removeItem('expirationTIme');

// };
// //유저정보
// // export const getUserActionHandler=(token)=>{
// // const URL='/member/me';
// // const response=GET(URL,createTokenHeader(token));
// // return response;
// // }

// //닉네임, 패스워드 변경
// // export const changeNicknameActionHander=(nickname,tooken)=>{
// // const URL='/member/nickname';
// // const changeNicknameObj={nickname};
// // const response=POST(URL.changeNicknameObj.createTOkenHeader(tooken));

// // return response;
// // }


// // export const changePasswordActionHander=(
// // exPassword,
// // newPassword,
// // tooken

// // )=>
// // {
// // const URL='member/password';
// // const changePasswordObj={exPassword,newPassword}
// // const response=POST(URL,changePasswordObj.createTOkenHeader(tooken));
// // return response;

// // }

