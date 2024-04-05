import axios from "axios";
import { useTokenStore } from "./token";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",
});

axiosInstance.interceptors.request.use(async (config) => {
  const tokenStore = useTokenStore();
  let accessToken = localStorage.getItem("accessToken");

  console.log(config.url)

  if (tokenStore.isTokenExpired(accessToken)) {
    const refreshToken=localStorage.getItem('refreshToken');
    await tokenStore.refreshAccessToken(config.url,accessToken,refreshToken);
  }
  config.headers['Authorization'] = `Bearer ${accessToken}`;
  return config;
}, (error) => {
  return Promise.reject(error);
});

export default axiosInstance;