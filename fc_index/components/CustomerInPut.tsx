import React, {JSX} from 'react';
import { Description, Field, Input, Label } from "@headlessui/react";

// 定义组件的 props 接口
interface CustomerInPutProps {
    label?: string;      // 可选的标签文本
    description?: string; // 可选的描述文本
    className?: string;  // 可选的外部 CSS 类名
    value: string;       // 输入框的当前值（必填）
    onChange: (s: string) => void; // 值改变时的回调函数（必填）
    onEnter?: () => void; // 按下 Enter 键时的回调函数（可选）
    placeholder?: string; // 输入框内的提示文本
    actionButton?: any; // 可选的 React 节点，用于在右侧显示操作按钮
    inputVisible?: boolean; // 新增属性：布尔标记，控制输入内容是否可见 (true: text, false: password)
}

// 导出 CustomerInPut 功能组件
export function CustomerInPut({
                                  label,
                                  description,
                                  className,
                                  value,
                                  onChange,
                                  onEnter,
                                  placeholder,
                                  actionButton,
                                  inputVisible=true
                              }: CustomerInPutProps) {
    // 使用 React 的 useState Hook 来管理输入框的聚焦状态
    const [isFocused, setIsFocused] = React.useState(false);

    // 处理输入框获得焦点的事件
    const handleFocus = () => setIsFocused(true);
    // 处理输入框失去焦点的事件
    const handleBlur = () => setIsFocused(false);

    // 逻辑判断：标签是否应该浮动。
    // 当输入框聚焦时 (isFocused 为 true)，或者输入框有内容 (value 不为空) 时，标签就应该浮动。
    const shouldFloatLabel = isFocused || value;

    return (
        // 最外层 div 负责整体布局和键盘事件处理。
        // 它被设置为 'relative'，作为绝对定位的浮动标签的定位上下文。
        <div
            className={`${className || ''} relative`}
            onKeyDown={(e) => {
                // 监听键盘事件，如果按下的是 "Enter" 键且提供了 onEnter 回调，则调用它。
                if (e.key === "Enter" && onEnter) {
                    onEnter();
                }
            }}
        >
            {/* Headless UI 的 Field 组件，作为表单元素的逻辑分组容器。 */}
            {/* 它也需要设置为 'relative'，以便正确地定位内部的操作按钮。 */}
            <Field className="w-full relative">
                {/* 描述文本部分：只有当 description 属性存在时才渲染。 */}
                {description && (
                    <Description className="text-sm text-on-surface-variant mb-2">
                        {description}
                    </Description>
                )}

                {/* 标签部分：只有当 label 属性存在时才渲染。 */}
                {label && (
                    <Label
                        // 标签的样式处理：
                        // 'absolute left-4': 绝对定位，距离左侧 1rem。
                        // 'transition-all duration-200 ease-in-out': 使位置和样式变化有平滑的动画效果。
                        // 'cursor-text': 鼠标悬停时显示文本光标。
                        className={`
                            absolute left-4 transition-all duration-200 ease-in-out cursor-text
                            ${!shouldFloatLabel // 条件样式：如果标签不应该浮动（即处于初始状态）
                            ? "text-on-background text-base font-medium" // 应用默认的文本颜色、基础字号和中等字重
                            : "text-sm text-primary -top-2 px-1 bg-surface-container-lowest rounded" // 否则（标签浮动状态），应用小字号、主色、向上移动、添加背景和内边距以“切出”边框
                        }
                        `}
                        style={{
                            // 动态设置 'top' 属性，控制标签的垂直位置：
                            // 如果浮动，移动到上方 (-0.5rem)。
                            // 否则，垂直居中在输入框的初始内边距区域。
                            // 'calc(0.625rem + 0.25rem)' 考虑了输入框的 `py-2.5` (0.625rem 顶部内边距) 和 `mt-1` (0.25rem 顶部外边距)。
                            top: shouldFloatLabel ? '-0.5rem' : 'calc(0.625rem + 0.25rem)',
                        }}
                    >
                        {label}
                    </Label>
                )}

                {/* 输入框元素：主输入字段。 */}
                <Input
                    className={`
                        mt-1 block w-full px-4 py-2.5                       /* 顶部外边距、块级显示、全宽、水平/垂直内边距 */
                        border border-outline rounded-lg                    /* 边框样式、颜色和圆角 */
                        bg-surface-container-low text-on-surface            /* 背景色和文本颜色 */
                        placeholder:text-on-surface-variant                 /* placeholder 文本颜色 */
                        focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary /* 聚焦状态：无轮廓、环形阴影、主色边框 */
                        transition-all duration-200 ease-in-out             /* 所有样式变化的平滑过渡 */
                        shadow-sm hover:shadow-md                           /* 默认阴影和鼠标悬停时的阴影 */
                        ${label ? 'pt-4' : ''}                               /* 条件顶部内边距：如果存在标签，增加顶部内边距以避免重叠 */
                        ${actionButton ? 'pr-10' : ''}                       /* 条件右侧内边距：如果存在操作按钮，增加右侧内边距为其留出空间 */
                    `}
                    value={value} // 绑定输入框的值到 'value' 属性
                    onChange={(e) => onChange(e.target.value)} // 监听值变化，并调用 'onChange' 回调
                    onFocus={handleFocus} // 绑定聚焦事件处理器
                    onBlur={handleBlur}   // 绑定失焦事件处理器
                    // 动态设置 placeholder 文本：
                    // 仅当没有标签（纯 placeholder 输入框）时，或者标签已经浮动时，才显示 placeholder。
                    // 这样可以避免标签和 placeholder 同时显示时的视觉混乱。
                    placeholder={!label || shouldFloatLabel ? placeholder : ''}
                    // 根据 inputVisible 属性设置输入框的类型
                    type={inputVisible ? 'text' : 'password'}
                />

                {/* 可选操作按钮：只有当 'actionButton' 属性存在时才渲染。 */}
                {actionButton && (
                    <div
                        // 绝对定位按钮在 Field 容器内部的右侧。
                        // 'right-2': 距离右边缘 0.5rem。
                        // 'top-1/2 -translate-y-1/2': 通过 transform 属性使按钮垂直居中。
                        className="absolute right-2 top-1/2 -translate-y-1/2"
                    >
                        {actionButton}
                    </div>
                )}
            </Field>
        </div>
    );
}