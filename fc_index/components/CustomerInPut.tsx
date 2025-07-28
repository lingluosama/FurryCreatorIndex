import React from 'react'; // 确保引入 React
import { Description, Field, Input, Label } from "@headlessui/react";

interface CustomerInPutProps {
    label?: string;
    description?: string;
    className?: string; // className 可以是可选的，以便外部不总是传入
    value: string;
    onChange: (s: string) => void;
    onEnter: () => void;
}

export function CustomerInPut({ label, description, className, value, onChange, onEnter }: CustomerInPutProps) {
    return (
        // 外层 div 负责整体布局和键盘事件
        <div className={`${className || ''} w-full`} // 确保 className 是可选时也能正确显示
             onKeyDown={(e) => {
                 if (e.key === "Enter") {
                     onEnter();
                 }
             }}
        >
            <Field className="w-full">
                {/* 标签 */}
                {label && ( // 只有当 label 存在时才渲染 Label
                    <Label className="text-on-surface text-base font-medium mb-1 block">
                        {label}
                    </Label>
                )}

                {/* 描述 */}
                {description && ( // 只有当 description 存在时才渲染 Description
                    <Description className="text-sm text-outline mb-2">
                        {description}
                    </Description>
                )}

                {/* 输入框本体 */}
                <Input
                    className={`
                        mt-1 block w-full px-4 py-2.5
                        border border-outline-variant rounded-lg
                        bg-surface-container text-on-surface
                        placeholder:text-on-surface-variant
                        focus:outline-none focus:ring-2 focus:ring-primary focus:border-primary
                        transition-all duration-200 ease-in-out
                        shadow-sm hover:shadow-md
                    `}
                    value={value}
                    onChange={(e) => onChange(e.target.value)}
                />
            </Field>
        </div>
    );
}