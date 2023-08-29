import React from "react";
import { useGoogleLogin } from "@react-oauth/google";
import axios from "axios";
import { googleLogout } from "@react-oauth/google";
import { hasGrantedAllScopesGoogle } from "@react-oauth/google";

const GoogleTest = () => {
  const googleSocialLogin = useGoogleLogin({
    scope:
      "email profile https://www.googleapis.com/auth/fitness.activity.read https://www.googleapis.com/auth/fitness.body.read https://www.googleapis.com/auth/fitness.location.read",
    onSuccess: async (res) => {
      console.log(res);
      axios
        .post(
          "http://localhost:8104/fitnessData",
          {},
          {
            headers: {
              Authorization: `Bearer ${res.code}`,
            },
          }
        )
        .then((response) => {
          console.log(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    },
    onError: (errorResponse) => {
      console.error(errorResponse);
    },
    flow: "auth-code",
  });

  const googleSocialLogout = googleLogout();

  return (
    <div>
      <button onClick={googleSocialLogin}>로그인</button>
      <button onClick={googleSocialLogout}>로그아웃</button>
    </div>
  );
};

export default GoogleTest;
