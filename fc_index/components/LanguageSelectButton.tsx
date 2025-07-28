import {useLanguage} from "@/context/language-provider";
import {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBook} from "@fortawesome/free-solid-svg-icons";


interface LanguageSelectButtonProps{
    w:string
    h:string
}

export default function  LanguageSelectButton({w,h}:LanguageSelectButtonProps){
    
    const languageContext = useLanguage();
    const [isHover, setIsHover] = useState(false)
    
    return(
        <div className={`bg-primary-container w-${w} min-h-${h} text-on-primary-container
            transition-all duration-300 hover:bg-primary
            flex flex-col rounded-2xl
        `}
            onMouseEnter={()=>setIsHover(true)}
             onMouseLeave={()=>setIsHover(false)}
        >
            <div className={`w-full flex items-center justify-center`}>
                <FontAwesomeIcon className={``} icon={faBook}></FontAwesomeIcon>
                {languageContext.language}  
            </div>
            <div 
                onClick={languageContext.toggleLanguage}
                className={`${isHover?`h-8`:`h-0`}  hover:bg-primary-container rounded-2xl hover:cursor-pointer w-full transition-all duration-300 text-center`}>
                {isHover?languageContext.language=="zh"?"en":"zh":null}
            </div>
        </div>
        
    )
    
}
