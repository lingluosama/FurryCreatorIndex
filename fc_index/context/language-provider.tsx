"use client"

import {createContext, ReactNode, useContext, useEffect, useState} from "react";
import {zhTranslations} from "@/public/language/zhTranslations";
import {enTranslations} from "@/public/language/enTranslations";
import {Translations} from "@/public/language/Translations";

type Language='en'|'zh'


type LanguageContextType={
    language:Language,
    toggleLanguage:()=>void,
    t:(page:keyof Translations,key:string)=>string
}

const LanguageContext = createContext<LanguageContextType|undefined>(undefined);

type ProviderProps={
    children:ReactNode
}

export function LanguageProvider({children}:ProviderProps){
    const [language, setLanguage] = useState<Language>("zh")
    
    useEffect(()=>{
        var savedLanguage = localStorage.getItem("language");
        const initialLanguage: Language = (savedLanguage === 'zh' || savedLanguage === 'en') ? savedLanguage : 'en';
        setLanguage(initialLanguage);
    },[])
    
    function toggleLanguage(){
        const targetLanguage = language=='en'?'zh':'en';
        
        setLanguage(targetLanguage)
        
        localStorage.setItem("language",targetLanguage)
    }

    const t = (page: keyof Translations, key: string): string => {
        // 根据当前语言选择对应的翻译数据
        const translations = language === 'zh' ? zhTranslations : enTranslations;

        // 获取指定页面的翻译对象
        const pageTranslations = translations[page];

        // 检查页面翻译对象是否存在且是对象类型
        if (pageTranslations && typeof pageTranslations === 'object') {
            // 直接通过 key 访问属性
            const value = (pageTranslations as any)[key]; // 使用 'any' 绕过 TypeScript 对动态键的严格检查

            // 确保获取到的值是字符串类型
            if (typeof value === 'string') {
                return value;
            }
        }
        // 如果页面或键不存在，或者值不是字符串，则返回原始键作为提示
        return `${page}.${key}`;
    };
    
    return(
        <LanguageContext.Provider value={{language,toggleLanguage,t}}>
            {children}
        </LanguageContext.Provider>
    )
    
}

export const useLanguage = (): LanguageContextType => {
    const context = useContext(LanguageContext);
    if (!context) {
        throw new Error("useLanguage 必须在 LanguageProvider 内部使用");
    }
    return context;
};
