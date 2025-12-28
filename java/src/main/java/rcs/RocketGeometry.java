package rcs;

import java.util.Objects;

/**
 * 参数化的火箭弹几何模型，用于描述模拟实验中的“目标”外形。
 */
public class RocketGeometry {
    public record CylinderParams(char orientationAxis, double outerRadius, double innerRadius,
                                 double start, double end) {
        public CylinderParams {
            if (orientationAxis != 'X' && orientationAxis != 'Y' && orientationAxis != 'Z') {
                throw new IllegalArgumentException("orientationAxis must be X, Y, or Z");
            }
        }
    }

    public record ConeParams(char orientationAxis, double bottomRadius, double topRadius,
                             double start, double end) {
        public ConeParams {
            if (orientationAxis != 'X' && orientationAxis != 'Y' && orientationAxis != 'Z') {
                throw new IllegalArgumentException("orientationAxis must be X, Y, or Z");
            }
        }
    }

    private final CylinderParams mainBody;
    private final ConeParams noseCone;
    private final CylinderParams tailSection;

    public RocketGeometry(CylinderParams mainBody, ConeParams noseCone, CylinderParams tailSection) {
        this.mainBody = Objects.requireNonNull(mainBody, "mainBody");
        this.noseCone = Objects.requireNonNull(noseCone, "noseCone");
        this.tailSection = Objects.requireNonNull(tailSection, "tailSection");
    }

    public CylinderParams mainBody() {
        return mainBody;
    }

    public ConeParams noseCone() {
        return noseCone;
    }

    public CylinderParams tailSection() {
        return tailSection;
    }

    /**
     * 根据题述“模拟实验的参数集”构建默认几何：
     * <ul>
     *   <li>主体：沿 Z 轴的圆柱，外半径 0.25 m，内半径 0（实心），Z 范围 0–6.14 m。</li>
     *   <li>顶部：沿 Z 轴的圆雉，底半径 0.25 m、顶半径 0 m，起始 Z 6.14 m，末端 6.64 m（取 0.5 m 作为锥长）。</li>
     *   <li>尾部：沿 X 轴的圆柱，外半径 0.20 m，内半径 0，X 范围 0–0.50 m。</li>
     * </ul>
     */
    public static RocketGeometry experimentDefaults() {
        CylinderParams body = new CylinderParams('Z', 0.25, 0.0, 0.0, 6.14);
        ConeParams cone = new ConeParams('Z', 0.25, 0.0, 6.14, 6.64);
        CylinderParams tail = new CylinderParams('X', 0.20, 0.0, 0.0, 0.50);
        return new RocketGeometry(body, cone, tail);
    }
}

