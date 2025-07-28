import {useEffect, useState} from "react";
import {AnimatePresence, motion} from "framer-motion";

interface CarouselBackgroundProps{
    duration:number,
    className?:string
}

export function CarouselBackground({duration,className}:CarouselBackgroundProps){
    const imageList=[
        "https://ts1.tc.mm.bing.net/th/id/R-C.a1eadb70ea3fe8883ebc0cbff0208044?rik=cWXALJnoRQl3yw&riu=http%3a%2f%2fwww.houqijun.vip%2fAttached%2fimage%2f20220803%2f20220803103222_88251.png&ehk=obzmfIV90PTF5MyTRRc1hoXDtHLZ%2bfBGhYPFcsW93v4%3d&risl=&pid=ImgRaw&r=0",
        "https://ts1.tc.mm.bing.net/th/id/R-C.15e970cd0765096178a6da16993cfbb1?rik=IT5KfevidZcTig&riu=http%3a%2f%2fimg.pconline.com.cn%2fimages%2fupload%2fupc%2ftx%2fwallpaper%2f1210%2f22%2fc0%2f14558824_1350879506501.jpg&ehk=X9ro%2fg%2fGTmsglVrbV%2bmy8c3wsAvcHseqcEhsf80RMWA%3d&risl=&pid=ImgRaw&r=0",
        "https://pic3.zhimg.com/v2-e52354ffdbd94a8e0a7649eacd34a788_r.jpg?source=1940ef5c",
    ]

    const [index, setIndex] = useState(0)
    
    useEffect(()=>{
        
        const timer = setInterval(()=>{
            setIndex(prevState => (prevState+1)%imageList.length)
        },duration*1000);
        
        return () => clearInterval(timer);
        
    },[duration])
    
    if(!imageList||imageList.length<1)return null;
    
    return (
        <div className={`relative w-full h-full overflow-hidden object-cover ${className}`}>
            {imageList.length>1?
                (<AnimatePresence initial={false} mode={"wait"}>
                <motion.img
                 key={index}
                 src={imageList[index]}
                 alt={`background-${index+1}`}
                 className={`absolute inset-0 w-full h-full object-cover`}
                 initial={{opacity:0}}
                 animate={{opacity:1}}
                 exit={{opacity:0}}
                 transition={{duration:1.5,ease:"easeInOut"}}
                >
                </motion.img>
            </AnimatePresence>):
                (<img
                    src={imageList[index]}
                    alt={`Background ${index + 1}`}
                    className="absolute inset-0 w-full h-full object-cover"
                />)}
        </div>

    )

}
