import { motion, Variants, Transition } from 'framer-motion';
import React, {ReactNode} from "react";


interface AnimationDefinition{
    initial:{opacity:number;y?:number,x?:number}
    animate:{opacity:number;y?:number,x?:number}
    transition:Transition
}

const animations:{[key:string]:AnimationDefinition}={
    fadeInUp: {
        initial: { opacity: 0, y: 70 }, // 初始状态：透明，从下方px处开始
        animate: { opacity: 1, y: 0 },   // 结束状态：完全不透明，回到原始位置
        transition: { duration: 0.8, ease: "easeOut" }, // 动画持续时间0.5秒，缓出效果
    },
    fadeInRight: {
        initial: { opacity: 0, x: -50 }, // 初始状态：透明，从左方px处开始
        animate: { opacity: 1, x: 0 },   // 结束状态：完全不透明，回到原始位置
        transition: { duration: 0.5, ease: "easeOut" }, // 动画持续时间0.5秒，缓出效果
    },
    
}

interface AnimatedContainerProps{
    direction:'up'|'down',
    children:ReactNode,
    className?:string,
}

export function AnimatedContainer({
    direction,
    children,
    className,
                                  }:AnimatedContainerProps){
    const animation=direction==="down"?animations.fadeInUp:animations.fadeInRight
    return(
        <motion.div
            //初始状态
            initial={animation.initial}
            //目标状态
            animate={animation.animate}
            //过度属性
            transition={animation.transition}
            className={className}
        >
            {children}
        </motion.div>
    )
    
}
