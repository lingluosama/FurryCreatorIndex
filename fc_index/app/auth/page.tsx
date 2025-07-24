"use client"

import {useTheme} from "@/context/theme-provider";
import {AnimatedContainer} from "@/components/AnimatedContainer";
import {AnimatedText} from "@/components/AnimatedText";
import {useLanguage} from "@/context/language-provider";
import {useState} from "react";
import {CarouselBackground} from "@/components/CarouselBackground";

export default function Page(){
    const {theme,toggleTheme} = useTheme();
    const languageContext = useLanguage();
    const [isLogin, setIsLogin] = useState(true)
    
    
    
    return(
        <div
            // onClick={toggleTheme}    
            className={`w-screen h-screen
            flex items-center flex-col space-y-12`}>
            <div className={`absolute bg-background w-full h-full -z-10`}>
                <CarouselBackground duration={3} className={`opacity-30`}></CarouselBackground>
            </div>
            
            {/* 语言切换和主题切换*/}
            <div className={`absolute right-0 top-0 w-1/12 flex flex-row`}>
                
            </div>
            
            {/*顶部站位*/}
            <div className={`w-full h-1/16`}></div>
            
            {/*动态欢迎词*/}
            <div className={` w-full flex items-center flex-col`}>
                <div className={`w-2/3 flex flex-row justify-items-start p-3`}>
                    <AnimatedText
                        className={`text-on-surface text-start text-5xl text-on-surface flex flex-wrap`}
                        text={
                        isLogin?
                        languageContext.t('authPage','title_col_login_1'):
                        languageContext.t('authPage','title_col_reg_1')}>
                    </AnimatedText>
                </div>  
                <div className={`w-2/3 flex flex-row justify-items-end p-3`}>
                    <AnimatedText
                        className={`w-full text-on-surface text-end  text-5xl text-on-surface`}
                        text={
                            isLogin?
                                languageContext.t('authPage','title_col_login_2'):
                                languageContext.t('authPage','title_col_reg_2')}>
                    </AnimatedText>
                </div>

            </div>

            
            <AnimatedContainer direction={"down"} className={` w-1/3 h-1/3 
            flex flex-col items-center 
            rounded-2xl shadow-2xl bg-surface border-2 border-outline`}>
                <div className={``}></div>
            </AnimatedContainer>

        </div>

    )


}
