"use client"

import {useTheme} from "@/context/theme-provider";
import {AnimatedContainer} from "@/components/layout/AnimatedContainer";
import {AnimatedText} from "@/components/layout/AnimatedText";
import {useLanguage} from "@/context/language-provider";
import {useState} from "react";
import {CarouselBackground} from "@/components/layout/CarouselBackground";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBook, faMoon, faSun} from "@fortawesome/free-solid-svg-icons";
import LanguageSelectButton from "@/components/LanguageSelectButton";
import {CustomerInPut} from "@/components/CustomerInPut";

export default function Page(){
    const {theme,toggleTheme} = useTheme();
    const languageContext = useLanguage();
    const [isLogin, setIsLogin] = useState(true)
    const [email, setEmail] = useState("")
    
    
    return(
        <div
            // onClick={toggleTheme}    
            className={`w-screen h-screen transition-all duration-300
            flex items-center flex-col space-y-12`}>
            <div className={`absolute bg-background w-full h-full -z-10`}>
                <CarouselBackground duration={3} className={`opacity-30`}></CarouselBackground>
            </div>
            
            {/* 语言切换和主题切换*/}
            <div className={`absolute right-0  transition-all duration-300 top-6 w-1/12 flex flex-row  space-x-6`}>
                {
                    <FontAwesomeIcon
                        className={`transition-all  duration-300 hover:cursor-pointer hover:scale-120 hover:text-inverse-primary`}    
                        size={"lg"} icon={theme==="dark"?faSun:faMoon} onClick={toggleTheme} />
                }
                <LanguageSelectButton w={`16`} h={`12`} ></LanguageSelectButton>
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
                <CustomerInPut 
                    className={`w-2/3`}
                    label={languageContext.t('authPage',"email")}
                    value={email} 
                    onChange={(s)=>setEmail(s)} 
                    onEnter={()=>{}}></CustomerInPut>
                <div className={``}></div>
            </AnimatedContainer>

        </div>

    )


}
