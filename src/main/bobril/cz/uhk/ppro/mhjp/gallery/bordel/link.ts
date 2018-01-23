import * as b from 'bobril'


export interface IData {
    text: any;
    link: string;
    onClick?: () => void;
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}

export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.tag = "a";
        me.children = [
            ctx.data.text
        ]
    },
    onClick(ctx: IContext) {
        b.runTransition(b.createRedirectPush(ctx.data.link));
        return true;
    }
});

