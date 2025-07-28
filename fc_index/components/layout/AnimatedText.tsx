import {motion,Variants} from "framer-motion";
import React from "react";

const containerVariants:Variants={
    hidden:{opacity:0},
    visible:{
        opacity:1,
        transition:{
            delayChildren:0.1,
            staggerChildren:0.05
        }
    }
    
}

const itemVariants:Variants={
    hidden:{opacity:0,y:20},
    visible:{opacity:1,y:0}
}

interface AnimatedTextProps{
    text:String;
    byWord?:boolean,
    className?:string,
}
export function AnimatedText({ text, byWord = false, className }: AnimatedTextProps) {
    // 根据 byWord prop 决定是拆分字符还是单词
    const items = byWord ? text.split(' ') : text.split('');

    return (
        <motion.div
            // 容器的动画变体
            variants={containerVariants}
            // 初始动画状态
            initial="hidden"
            // 目标动画状态
            animate="visible"
            // 额外的 CSS 类名
            className={className}
            transition={{duration:5,ease: "easeOut"}}
        >
            {items.map((item, index) => (
                // 每个字符或单词都用一个 motion.span 包裹
                <motion.span
                    key={index} 
                    variants={itemVariants}
                    // 为了确保每个字符/单词独立排列，保持 inline-block 样式
                    className={`inline-block `}
                >
                    {/* 处理空格：对于按字符动画，如果遇到空格，用 &#xa0; (不换行空格) 替代，以保持布局 */}
                    {item === ' ' ? '\u00A0' : item}
                    {/* 如果是按单词动画，并且不是最后一个单词，加一个普通空格 */}
                    {byWord && index < items.length - 1 && ' '}
                </motion.span>
            ))}
        </motion.div>
    );
}
