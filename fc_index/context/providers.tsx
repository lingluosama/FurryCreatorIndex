import {ReactNode} from "react";
import {ThemeProvider} from "@/context/theme-provider";
import {LanguageProvider} from "@/context/language-provider";


interface ProvidersProps{
    children:ReactNode
}

export default function Providers({children}:ProvidersProps){
    
    return(
        <LanguageProvider>
            <ThemeProvider>
                 {children}
            </ThemeProvider>
        </LanguageProvider>
    )

    
    
} 
