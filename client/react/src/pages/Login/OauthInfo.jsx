import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {loginRequest, loginSuccess, loginFailure} from '../../store/member/memberAction'
import { useDispatch } from 'react-redux';


const OauthInfo = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()

    useEffect(() => {
        // URL에서 쿼리 문자열을 추출합니다.
        const query = window.location.search;
    
        // 쿼리 문자열을 출력하여 확인합니다.
        console.log('Query String:', query);
    
        // URL에서 OauthInfo 쿼리 파라미터를 추출합니다.
        const urlParams = new URLSearchParams(query);
        const oauthInfoJson = urlParams.get('OauthInfo');
        
        dispatch(loginFailure())

        if (oauthInfoJson) {
          // JSON 형식의 문자열을 파싱합니다.
          const oauthInfo = JSON.parse(decodeURIComponent(oauthInfoJson));
          dispatch(loginSuccess(oauthInfo))
          localStorage.setItem("id", oauthInfo.id);
          console.log('OAuth Info:', oauthInfo);
          
          navigate("/")
        }
      }, []);
  return (
    <div>OauthInfo</div>
  )
}

export default OauthInfo