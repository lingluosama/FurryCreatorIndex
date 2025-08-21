"use client"

import {createContext, ReactNode, useContext, useEffect, useState} from "react";

type themeContextType={
    theme:string
    toggleTheme:()=>void
}

const ThemeContext=createContext<themeContextType|undefined>(undefined)

type ProviderProps={
    children: ReactNode
} 

export function ThemeProvider({children}:ProviderProps){
    const [theme, setTheme] = useState("dark")
    
    useEffect(()=>{
        const savedTheme = localStorage.getItem("theme");
        const systemDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        const initialTheme=savedTheme||(systemDark?"dark":"light")
        
        document.documentElement.classList.toggle('dark',initialTheme==="dark");
        setTheme(initialTheme)
    },[])
    
    const toggleTheme=()=>{
        setTheme(prev=>{
            const newTheme=prev==="light"?"dark":"light";
            localStorage.setItem('theme',newTheme)
            document.documentElement.classList.toggle("dark",newTheme==="dark");
            return newTheme;
        })
    }
    
    return(
        <ThemeContext.Provider value={{theme,toggleTheme}}>
            {children}
        </ThemeContext.Provider>
    )
}

export const useTheme=():themeContextType=>{
    const context = useContext(ThemeContext)
    if(!context){
        throw new Error("父级组件未找到ThemeContext,无法使用useTheme");
    }
    return context;
    
}
