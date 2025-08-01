"use client"

import { useTheme } from "@/context/theme-provider";
import { AnimatedContainer } from "@/components/layout/AnimatedContainer";
import { AnimatedText } from "@/components/layout/AnimatedText";
import { useLanguage } from "@/context/language-provider";
import { useEffect, useState } from "react";
import { CarouselBackground } from "@/components/layout/CarouselBackground";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMoon, faSun } from "@fortawesome/free-solid-svg-icons";
import LanguageSelectButton from "@/components/LanguageSelectButton";
import { CustomerInPut } from "@/components/CustomerInPut";
import { Button } from "@headlessui/react";

export default function Page() {
    const { theme, toggleTheme } = useTheme();
    const languageContext = useLanguage();

    const [isLogin, setIsLogin] = useState(true);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [emailVerifyInterval, setEmailVerifyInterval] = useState(0);
    const [name, setName] = useState("");
    const [verifyCode, setVerifyCode] = useState("")

    // 验证邮箱
    function verifyEmail() {
        if (emailVerifyInterval > 0) return;
        setEmailVerifyInterval(60);
    }

    // 验证间隔计时器
    useEffect(() => {
        let timer: ReturnType<typeof setInterval> | undefined;
        if (emailVerifyInterval > 0) {
            timer = setInterval(() => {
                setEmailVerifyInterval(prev => prev - 1);
            }, 1000);
        }
        return () => {
            if (timer !== undefined) {
                clearInterval(timer);
            }
        };
    }, [emailVerifyInterval]);

    // noinspection TypeScriptValidateTypes
    return (
        <div
            className={`w-screen h-screen transition-all duration-300 flex flex-col items-center justify-center relative overflow-hidden`}
        >
            {/* 背景层 */}
            <div className={`absolute bg-background w-full h-full -z-10`}>
                <CarouselBackground duration={3} className={`opacity-40`}></CarouselBackground>
            </div>

            {/* 语言切换和主题切换 - 放置在右上角 */}
            <div className={`absolute top-6 right-6 flex items-center space-x-4 z-10`}>
                <FontAwesomeIcon
                    className={`transition-all duration-300 text-on-surface hover:cursor-pointer hover:scale-120 hover:text-inverse-primary`}
                    size={"lg"} icon={theme === "dark" ? faSun : faMoon} onClick={toggleTheme}
                />
                <LanguageSelectButton w={`16`} h={`12`} />
            </div>

            {/* 动态欢迎词 - 居中且宽度适中 */}
            <div className={`w-full max-w-5xl text-center mb-8 px-4`}> 
                <div className={`p-3`}>
                    <AnimatedText
                        className={`text-on-surface text-4xl md:text-5xl lg:text-6xl font-extrabold flex flex-wrap justify-center leading-tight`} /* 调整字号，移除 break-before-avoid，增加 leading-tight */
                        text={
                            isLogin ?
                                languageContext.t('authPage', 'title_col_login_1') :
                                languageContext.t('authPage', 'title_col_reg_1')}
                    ></AnimatedText>
                </div>
                <div className={`p-3`}>
                    <AnimatedText
                        className={`text-on-surface text-4xl md:text-5xl lg:text-6xl font-extrabold flex flex-wrap justify-center leading-tight`} /* 调整字号，增加 leading-tight */
                        text={
                            isLogin ?
                                languageContext.t('authPage', 'title_col_login_2') :
                                languageContext.t('authPage', 'title_col_reg_2')}
                    ></AnimatedText>
                </div>
            </div>

            {/* 登录和注册表单 - 使用 key 强制动画重置 */}
            <AnimatedContainer
                key={isLogin ? "login" : `register`}
                direction={"down"}
                className={`w-full max-w-md p-8
                flex flex-col items-center
                rounded-2xl shadow-2xl bg-surface-container-high border-2 border-outline space-y-6`}
            >
                {isLogin ? (
                    <> {/* 登录表单内容 */}
                        <CustomerInPut
                            className={`w-full`}
                            label={languageContext.t('authPage', "email")}
                            value={email}
                            onChange={(s) => setEmail(s)}
                            onEnter={() => {/* 登录逻辑 */}}
                        />
                        <CustomerInPut
                            className={`w-full`}
                            value={password}
                            onChange={(s) => setPassword(s)}
                            label={languageContext.t('authPage', 'password')}
                            inputVisible={false}
                            onEnter={() => {/* 登录逻辑 */}}
                        />
                        <Button
                            className={`w-2/3 py-2.5 mt-4 bg-primary rounded-xl border-2 border-primary-container text-on-primary-container font-semibold transition-all hover:scale-105 hover:shadow-lg focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2`}
                        >
                            {languageContext.t("authPage", "login")}
                        </Button>
                    </>
                ) : (
                    <> {/* 注册表单内容 */}
                        <CustomerInPut
                            className={`w-full`}
                            label={languageContext.t('authPage', "email")}
                            value={email}
                            actionButton={
                                <Button
                                    disabled={emailVerifyInterval > 0}
                                    onClick={verifyEmail}
                                    className={`px-4 py-1 rounded-lg text-sm bg-secondary-container text-on-secondary-container transition-all hover:scale-105 disabled:opacity-50 disabled:cursor-not-allowed focus:outline-none focus:ring-2 focus:ring-secondary`}
                                >
                                    {!emailVerifyInterval > 0 ?
                                        languageContext.t("authPage", "verify_email") : emailVerifyInterval}
                                </Button>
                            }
                            onChange={(s) => setEmail(s)}
                            onEnter={() => { /* 注册逻辑 */ }}
                        />
                        <CustomerInPut
                            className={`w-full`}
                            label={languageContext.t("authPage", "verify_code")}
                            value={verifyCode}
                            onChange={(s) => setVerifyCode(s)}
                            onEnter={() => { /* 注册逻辑 */ }}
                        />
                        <CustomerInPut
                            className={`w-full`}
                            label={languageContext.t("authPage", "name")}
                            value={name}
                            onChange={(s) => setName(s)}
                            onEnter={() => { /* 注册逻辑 */ }}
                        />
                        <CustomerInPut
                            className={`w-full`}
                            value={password}
                            label={languageContext.t("authPage", "password")}
                            onChange={(s) => setPassword(s)}
                            inputVisible={false}
                            onEnter={() => { /* 注册逻辑 */ }}
                        />
                        <Button
                            className={`w-2/3 py-2.5 mt-4 bg-primary rounded-xl border-2 border-primary-container text-on-primary-container font-semibold transition-all hover:scale-105 hover:shadow-lg focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2`}
                        >
                            {languageContext.t("authPage", "register")}
                        </Button>
                    </>
                )}
            </AnimatedContainer>

            {/* 切换登录/注册模式的浮动按钮 - 放在右下角 */}
            <div
                onClick={() => setIsLogin(!isLogin)}
                // 调整为药丸形状，增加宽度以容纳文本，并根据文本内容动态调整字体大小
                className={`absolute bottom-8 right-8 px-6 py-3 flex items-center justify-center
                  rounded-full bg-tertiary text-on-tertiary text-center text-sm font-semibold
                  shadow-lg transition-all hover:scale-110 hover:cursor-pointer z-10 whitespace-nowrap`} /* 关键：px-6 py-3 调整尺寸，text-sm 减小字体，whitespace-nowrap 防止强制换行 */
            >
                {isLogin ? languageContext.t("authPage", "to_register") : languageContext.t("authPage", "to_login")}
            </div>
        </div>
    );
}