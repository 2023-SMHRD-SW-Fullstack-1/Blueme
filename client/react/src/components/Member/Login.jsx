import React from "react";
import { Link } from "react-router-dom";
import logo from "../../img/logo.png";

const Login = () => {
  return (
    <div className="bg-custom-blue">
      <section className="h-screen flex flex-col items-center justify-center">
        <div className="container px-4 sm:px-6 md:px-8 lg:px-24 py-4 sm:py-6 md:py-8 lg:py-24">
          <div className=" grid-cols-1 md:flex justify-center  md:h-full flex-wrap lg:justify-between gap-y-[2em] md:gap-y[0em] flex flex-col items-center">
            <div className="w-full sm:w-full md:w-auto lg:w-auto xl:max-w-lg mb[2em} md:m-b[0em]">
              <Link to="/Main">
                <img src={logo} className="ml-2 w-full h-auto max-h-[30vh] sm:max-h-[30vh] object-contain" alt="" />
              </Link>
            </div>

            <div className="md:w-8/12 lg:ml-6 lg:w-5/12 flex flex-col items-center">
              <form>
                <div className="relative mb-6" data-te-input-wrapper-init>
                  <input
                    type="text"
                    className="peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear focus:placeholder:opacity-100 data-[te-input-state-active]:placeholder:opacity-100 motion-reduce:transition-none dark:text-neutral-200 dark:placeholder:text-neutral-200 [&:not([data-te-input-placeholder-active])]:placeholder:opacity-0"
                    id="exampleFormControlInput3"
                    placeholder="Email address"
                  />
                  <label
                    for="exampleFormControlInput3"
                    className="pointer-events-none absolute left-3 top-0 mb-0 max-w-[90%] origin-[0_0] truncate pt-[0.37rem] leading-[2.15] text-neutral-500 transition-all duration-200 ease-out peer-focus:-translate-y-[0.75rem] peer-focus:scale-[0.8] peer-focus:text-primary peer-data-[te-input-state-active]:-translate-y-[1.15rem] peer-data-[te-input-state-active]:scale-[0.8] motion-reduce:transition-none dark:text-neutral-200 dark:peer-focus:text-primary"
                  >
                    Email address
                  </label>
                </div>

                <div className="relative mb-6 " data-te-input-wrapper-init>
                  <input
                    type="password"
                    className="peer block min-h-[auto] w-full rounded border-0 bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear focus:placeholder:opacity-100 data-[te-input-state-active]:placeholder:opacity-100 motion-reduce:transition-none dark:text-neutral-200 dark:placeholder:text-neutral-200 [&:not([data-te-input-placeholder-active])]:placeholder:opacity-0"
                    id="exampleFormControlInput33"
                    placeholder="Password"
                  />
                  <label
                    for="exampleFormControlInput33"
                    className="pointer-events-none absolute left-3 top-0 mb-0 max-w-[90%] origin-[0_0] truncate pt-[0.37rem] leading-[2.15] text-neutral-500 transition-all duration-200 ease-out peer-focus:-translate-y-[0.75rem] peer-focus:scale-[0.8] peer-focus:text-primary peer-data-[te-input-state-active]:-translate-y-[1.15rem] peer-data-[te-input-state-active]:scale-[0.8] motion-reduce:transition-none dark:text-neutral-200 dark:peer-focus:text-primary"
                  >
                    Password
                  </label>
                </div>

                <div className="mb-6 flex items-center justify-between ">
                  <div className="mb-[0.125rem] block min-h-[1.5rem] pl-[1.5rem]"></div>

                  <a
                    href="#!"
                    className="text-custom-white text-primary transition duration-150 ease-in-out hover:text-primary-600 focus:text-primary-600 active:text-primary-700 dark:text-primary-400 dark:hover:text-primary-500 dark:focus:text-primary-500 dark:active:text-primary-600"
                  >
                    Forgot password?
                  </a>
                </div>

                <button
                  type="submit"
                  className=" bg-custom-blue inline-block w-full rounded bg-primary px-7 pb-2.5 pt-3 text-sm font-medium uppercase leading-normal  text-custom-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
                  data-te-ripple-init
                  data-te-ripple-color="light"
                >
                  Sign in
                </button>

                <div className="my-4">
                  <Link to="/Join">
                    <button
                      type="submit"
                      className=" bg-custom-blue py-10 inline-block w-full rounded bg-primary px-7 pb-2.5 pt-3 text-sm font-medium uppercase leading-normal  text-custom-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
                      data-te-ripple-init
                      data-te-ripple-color="light"
                    >
                      Sign up
                    </button>
                  </Link>
                </div>

                <div className="my-4 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300">
                  <p className="mx-4 mb-0 text-center font-semibold text-custom-white">OR</p>
                </div>

                <a
                  className="mb-3 flex w-full items-center justify-center rounded bg-primary px-7 pb-2.5 pt-3 text-center bg-custom-blue text-custom-white font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#FEE500] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(254,229,0,0.3),0_4px_18px_0_rgba(254,229,0,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(254,229,0,0.3), 04_x18x 00rgba (254 ,229 ,00 .2 ) ] focus :outline -none focus :ring - 00 active :bg -primary -700 active :shadow - [08 x09x _ -04 _x rgba (254 ,22__9_,__00 _.3__),___04 __xx___18 ___xx___00____r_gba__(25______4__,__22_____9__,__20_____.2__)_]dark:shadow-[04x09x_-04_xrgba_(25__,22__,90__.5_)_]dark:hover:shadow-[08_x09_x-_04__xrgba_(25__,22__,90__.2__),__04__xx__18__xx___00___r_gba__(25__,__11___5__,__20_____.1__)_]dark:focus:shadow-[08x09x_-04_xrgba_(95_,115_,20_.2_),04_x18_x00_r_gba_(95_,115_,20_.1_)_]dark:_active:_shado_w_[08_x09_x-_04__xrgba_(95__,115__,20__.2__),__04__xx__18__xx___00___r_gba__(95__,__11___5__,__20_____.1__)]"
                  // style="background-color: #3b5998"
                  href="#!"
                  role="button"
                  data-te-ripple-init
                  data-te-ripple-color="light"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="mr-2 h-3.5 w-3.5"
                    fill="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path d="M9 8h-3v4h3v12h5v-12h3.642l.358-4h-4v-1.667c0-.955.192-1.333 1.115-1.333h2.885v-5h-3.808c-3.596 0-5.192 1.583-5.192 4.615v3.385z" />
                  </svg>
                  Continue with KakaoTalk
                </a>
                <a
                  className="mb-3 flex w-full items-center justify-center bg-custom-blue rounded bg-info px-7 pb-2.5 pt-3 text-center text-sm font-medium uppercase leading-normal text-custom-white shadow-[0_4px_9px_-4px_#FFFFFF] transition duration-150 ease-in-out hover:bg-info-600 hover:shadow-[0_8px_9px_-4px_rgba(255,255,255,0.3),0_4px_18px_0_rgba(255,255,255,0.2)] focus:bg-info-600 focus:shadow-[0_8px_9px_-4px_rgba(255,255,255,0.3),0_4px_18px_0_rgba(255,255,255,0.2)] focus:outline-none focus:ring-0 active:bg-info-700 active:shadow-[0_8px_9px_-4px_rgba(255,255,255,0.3),0_4px_18px_0_rgba(255,255,255,0.2)] dark:shadow-[0_4px_9px_-4px_rgba(255,255,255,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(255,255,255,0.2),0_4px_18px_0_rgba(255,255,255,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(255,255,255,0.2),0_4px_18px_0_rgba(255,255,255,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(255,255,255,0.2),0_4px_18px_0_rgba(255,255,255,0.1)]"
                  // style="background-color: #55acee"
                  href="#!"
                  role="button"
                  data-te-ripple-init
                  data-te-ripple-color="light"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="mr-2 h-3.5 w-3.5"
                    fill="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z" />
                  </svg>
                  Continue with Google
                </a>
              </form>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Login;
