// next.config.js
const nextConfig = {
    webpack: (config, { isServer, webpack }) => {
        // 解决 node:timers 问题
        config.plugins.push(
            new webpack.NormalModuleReplacementPlugin(
                /^node:/,
                (resource) => {
                    resource.request = resource.request.replace(/^node:/, "");
                }
            )
        );

        // 添加必要的 polyfill
        config.resolve.fallback = {
            ...config.resolve.fallback,
            timers: require.resolve("timers-browserify"),
            events: require.resolve("events/"),
            stream: require.resolve("stream-browserify"),
        };

        return config;
    },
};

module.exports = nextConfig;