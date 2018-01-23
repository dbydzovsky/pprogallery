import * as b from 'bobril';
import * as bs from 'bobrilstrap';

export interface IData {
}

export interface IContext extends b.IBobrilCtx {
    data: IData;
}


export const create = b.createComponent<IData>({
    render(ctx: IContext, me: b.IBobrilNode) {
        me.className = "container";
        me.style = {
            minHeight: 40, fontSize: 17, width: "45%", fontFamily: "Georgia", textAlign: "center", backgroundColor: "",
        };
        me.children = [
            bs.Row({style:{}}, [
                bs.Pills({style: bs.typography.textCenter}, [
                    bs.Pill({}, bs.A({href: '#/'}, 'Home')),
                    bs.Pill({}, bs.A({href: '#/trendy'}, "Trendy")),
                    bs.Pill({}, bs.A({href: '#/gallery'}, 'Gallery')),
                    bs.Pill({}, bs.A({href: '#/find'}, 'Find')),
                    bs.Pill({}, bs.A({href: '#/login'}, 'Login')),
                ])
            ])
        ]
    }
});
